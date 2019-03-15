package pl.somehost.contactmanager.messageclient.sms;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.config.sms.SmsConfiguration;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;
import pl.somehost.contactmanager.domain.message.SmsMessage;
import pl.somehost.contactmanager.messageclient.IMessageClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class SmsClient implements IMessageClient<SmsMessage> {

    @Autowired
    SmsConfiguration smsConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsClient.class);

    @Override
    public MessageStatus sendMessage(SmsMessage smsMessage) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(smsConfiguration.getSmsSocketTimeout())
                .setConnectTimeout(smsConfiguration.getSmsConnectTimeout())
                .build();

        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(smsConfiguration.getSmsHost())
                    .setPort(smsConfiguration.getSmsPort())
                    .setPath(smsConfiguration.getSmsPath())
                    .setParameter(smsConfiguration.getSmsPhoneParaneter(), smsMessage.getPhoneNumber())
                    .setParameter(smsConfiguration.getSmsMessageParaneter(), smsMessage.getMessageText())
                    .build();

            LOGGER.info("SMS URI : " + uri.toString());

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setConfig(requestConfig);

            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost, context);
            Integer responseStatusCode = response.getStatusLine().getStatusCode();

            LOGGER.info("SMS Response HTTP status code: " + responseStatusCode);

            if (!responseStatusCode.equals(HttpServletResponse.SC_OK)) {
                LOGGER.info("Throwing exception as a result of HTTP status code other than 200: MessageSendException");
                return MessageStatus.NOT_SEND;
            }

        } catch (URISyntaxException | IOException e) {
            LOGGER.info("Error sending message : " + e.getMessage());
            return MessageStatus.NOT_SEND;
        }

        LOGGER.info("Message was send");
        return MessageStatus.SEND;
    }
}

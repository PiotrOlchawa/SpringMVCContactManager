package pl.somehost.contactmanager.client.sms;

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
import pl.somehost.contactmanager.domain.SmsMessage;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.SmsException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class SmsClient {

    @Autowired
    SmsConfiguration smsConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsClient.class);

    Integer responseStatusCode = HttpServletResponse.SC_NOT_FOUND;

        public ContactManagerResponseMessage sendMessage(SmsMessage smsMessage){

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
                        .setParameter(smsConfiguration.getSmsPhoneParaneter(),smsMessage.getPhoneNumber())
                        .setParameter(smsConfiguration.getSmsMessageParaneter(),smsMessage.getMessageText())
                        .build();

                LOGGER.info("SMS URI : "  + uri.toString());

                HttpPost httpPost = new HttpPost(uri);
                httpPost.setConfig(requestConfig);

                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

                HttpClientContext context = HttpClientContext.create();

                CloseableHttpResponse response = closeableHttpClient.execute(httpPost,context);

                Integer responseStatusCode = response.getStatusLine().getStatusCode();

                LOGGER.info("SMS Response HTTP status code: " + responseStatusCode);

                if(!responseStatusCode.equals(HttpServletResponse.SC_OK)){
                    LOGGER.info("Throwing exception as a result of HTTP status code other than 200: SmsException");
                    throw new SmsException("Can't send sms : SMS Response HTTP status code: " + responseStatusCode);
                }


            } catch (URISyntaxException | IOException e) {
                LOGGER.info("Error sending message");
                throw new SmsException("Can't send sms : " + e.getMessage());
            }

            return new ContactManagerResponseMessage("Message to: " + smsMessage.getPhoneNumber()
                    +" was send sucessfully with status code: " +responseStatusCode );
        }





}

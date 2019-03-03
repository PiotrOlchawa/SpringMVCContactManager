package pl.somehost.contactmanager.client.sms;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
                        .setParameter(smsConfiguration.getSmsMessageParaneter(),smsMessage.getPhoneNumber())
                        .build();

                HttpPost httpPost = new HttpPost(uri);
                httpPost.setConfig(requestConfig);

                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

                HttpClientContext context = HttpClientContext.create();

                CloseableHttpResponse response = closeableHttpClient.execute(httpPost,context);

                Integer responseStatusCode = response.getStatusLine().getStatusCode();

                if(responseStatusCode != HttpServletResponse.SC_OK){
                    throw new SmsException("Can't send sms : status code " + responseStatusCode);
                }


            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }

            return new ContactManagerResponseMessage("Message to: " + smsMessage.getPhoneNumber()
                    +" was send sucessfully with status code: " +responseStatusCode );
        }





}

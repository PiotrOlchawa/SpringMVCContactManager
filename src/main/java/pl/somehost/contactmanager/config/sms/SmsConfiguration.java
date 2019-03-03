package pl.somehost.contactmanager.config.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsConfiguration {

    @Value("${smsHost}")
    private String smsHost;
    @Value("${smsPort}")
    private Integer smsPort;
    @Value("${smsPath}")
    private String smsPath;
    @Value("${smsMessageParameter}")
    private String smsMessageParaneter;
    @Value("${smsPhoneParameter}")
    private String smsPhoneParaneter;
    @Value("${smsConnectTimeout}")
    private Integer smsConnectTimeout;
    @Value("${smsSocketTimeout}")
    private Integer smsSocketTimeout;

    public String getSmsHost() {
        return smsHost;
    }

    public Integer getSmsPort() {
        return smsPort;
    }

    public String getSmsPath() {
        return smsPath;
    }

    public String getSmsMessageParaneter() {
        return smsMessageParaneter;
    }

    public String getSmsPhoneParaneter() {
        return smsPhoneParaneter;
    }

    public Integer getSmsConnectTimeout() {
        return smsConnectTimeout;
    }

    public Integer getSmsSocketTimeout() {
        return smsSocketTimeout;
    }
}

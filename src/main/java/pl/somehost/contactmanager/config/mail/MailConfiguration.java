package pl.somehost.contactmanager.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Value("${mailMessage.protocol}")
    private String protocol;
    @Value("${mailMessage.host}")
    private String host;
    @Value("${mailMessage.port}")
    private int port;
    @Value("${mailMessage.smtp.socketFactory.port}")
    private int socketPort;
    @Value("${mailMessage.smtp.auth}")
    private boolean auth;
    @Value("${mailMessage.smtp.ssl.enable}")
    private boolean ssl;
    @Value("${mailMessage.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mailMessage.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${mailMessage.smtp.debug}")
    private boolean debug;
    @Value("${mailMessage.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${mailMessage.from}")
    private String username;
    @Value("${mailMessage.password}")
    private String password;

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.ssl.enable", ssl);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailProperties.put("mail.smtp.starttls.required", startlls_required);
        mailProperties.put("mail.smtp.socketFactory.port", socketPort);
        mailProperties.put("mail.smtp.debug", debug);
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.put("mail.smtp.socketFactory.fallback", fallback);

        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}

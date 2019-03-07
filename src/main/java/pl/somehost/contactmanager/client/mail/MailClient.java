package pl.somehost.contactmanager.client.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.MailMessage;
import pl.somehost.contactmanager.domain.message.MessageStatus;

@Component
public class MailClient {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${mailMessage.from}")
    private String from;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

    private MimeMessagePreparator createMimeMessage(final MailMessage mailMessage) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mailMessage.getMailTo());
            messageHelper.setSubject(mailMessage.getSubject());
            messageHelper.setText(mailMessage.getMessage());
            messageHelper.setFrom(from);
        };
    }

    public MessageStatus sendMail(MailMessage mailMessage) {
        try {
            javaMailSender.send(createMimeMessage(mailMessage));
            LOGGER.info("Mail Message was send");
            return MessageStatus.SEND;
        } catch (MailException e){
            return MessageStatus.NOT_SEND;
        }
    }
}

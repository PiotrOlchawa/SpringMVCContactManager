package pl.somehost.contactmanager.client.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.MailMessage;

@Component
public class MailClient {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${mail.from}")
    private String from;

    private MimeMessagePreparator createMimeMessage(final MailMessage mailMessage) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mailMessage.getMailTo());
            messageHelper.setSubject(mailMessage.getSubject());
            messageHelper.setText(mailMessage.getMessage());
            messageHelper.setFrom(from);
        };
    }

    public void sendMail(MailMessage mailMessage) {
        javaMailSender.send(createMimeMessage(mailMessage));
    }
}

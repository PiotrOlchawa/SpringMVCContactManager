package pl.somehost.contactmanager.client.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Mail;

@Component
public class MailClient {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${mail.from}")
    private String from;

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage());
            messageHelper.setFrom(from);
        };
    }

    public void sendMail(Mail mail) {
        javaMailSender.send(createMimeMessage(mail));
    }
}

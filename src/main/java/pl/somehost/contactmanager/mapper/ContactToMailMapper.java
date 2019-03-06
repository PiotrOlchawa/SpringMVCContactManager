package pl.somehost.contactmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.message.MailMessage;

@Component
public class ContactToMailMapper {

    @Autowired
    LoggedUserGetter loggedUserGetter;
    @Value("${mailMessage.message.subject}")
    private String subject;

    public MailMessage mapContactDtoToMail(Contact contact, MailMessage mailMessage) {
        mailMessage.setMailTo(contact.getEmail());
        mailMessage.setSubject(subject + " " + loggedUserGetter.getLoggedUserName());
        return mailMessage;
    }
}

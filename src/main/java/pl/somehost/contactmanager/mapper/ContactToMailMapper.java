package pl.somehost.contactmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.Mail;

@Component
public class ContactToMailMapper {

    @Autowired
    LoggedUserGetter loggedUserGetter;
    @Value("${mail.message.subject}")
    private String subject;

    public Mail mapContactDtoToMail(Contact contact, Mail mail){
        mail.setMailTo(contact.getEmail());
        mail.setSubject(subject + loggedUserGetter.getLoggedUserName());
        return mail;
    }
}

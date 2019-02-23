package pl.somehost.contactmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.Mail;
import pl.somehost.contactmanager.domain.dto.ContactDto;

@Component
public class ContactDtoToMailMapper {

    @Autowired
    LoggedUserGetter loggedUserGetter;
    @Value("${mail.message.subject}")
    private String subject;

    public Mail mapContactDtoToMail(ContactDto contactDto, Mail mail){
        mail.setMailTo(contactDto.getEmail());
        mail.setSubject(subject + loggedUserGetter.getLoggedUserName());
        return mail;
    }
}

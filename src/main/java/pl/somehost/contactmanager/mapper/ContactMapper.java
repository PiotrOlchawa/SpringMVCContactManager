package pl.somehost.contactmanager.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.security.LoggedUserGetter;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.message.MailMessage;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.SmsMessage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    @Autowired
    LoggedUserGetter loggedUserGetter;
    @Value("${mailMessage.message.subject}")
    private String subject;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactMapper.class);

    public Contact mapContactDtoToContact(ContactDto contactDto) {

        User currentUser = loggedUserGetter.getLoggedUser();

        LOGGER.info("Current User AdressBook id " + currentUser.getAdressBook().getId());
        return new Contact.Builder()
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .id(contactDto.getId())
                .aptNumber(contactDto.getAptNumber())
                .email(contactDto.getEmail())
                .streetAdress(contactDto.getStreetAdress())
                .telephone(contactDto.getTelephone())
                .zipCode(contactDto.getZipCode())
                .adressBook(currentUser.getAdressBook())
                .build();
    }

    public List<ContactDto> mapContactListToContactDtoList(List<Contact> contactList) {

        return contactList.stream().map(l -> new ContactDto(l.getId(), l.getFirstName(), l.getLastName(), l.getStreetAdress(),
                l.getZipCode(), l.getAptNumber(), l.getTelephone(), l.getEmail())).collect(Collectors.toList());
    }

    public ContactDto mapContactToContactDto(Contact contact) {

        return new ContactDto.Builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .streetAdress(contact.getStreetAdress())
                .zipCode(contact.getZipCode())
                .aptNumber(contact.getAptNumber())
                .telephone(contact.getTelephone())
                .email(contact.getEmail())
                .build();
    }

    public MailMessage mapContactToMail(Contact contact, MailMessage mailMessage) {
        mailMessage.setMailTo(contact.getEmail());
        mailMessage.setSubject(subject + " " + loggedUserGetter.getLoggedUserName());
        return mailMessage;
    }

    public SmsMessage mapContactToSms(Message message){
        return new SmsMessage(message, message.getContact().getTelephone());
    }

    public SmsMessage mapContactToSmsMessage(Contact contact,SmsMessage smsMessage){
        smsMessage.setPhoneNumber(contact.getTelephone());
        return smsMessage;
    }

}

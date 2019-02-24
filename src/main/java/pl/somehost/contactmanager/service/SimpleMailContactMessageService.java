package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.client.mail.MailClient;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.Mail;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.mapper.ContactToMailMapper;

@Service
public class SimpleMailContactMessageService implements MailContactMessageService {

    @Autowired
    MailClient mailClient;
    @Autowired
    ContactToMailMapper contactToMailMapper;
    @Autowired
    ContactService contactService;

    @Override
    public void sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        contact.getMessageList().add(message);
        contactService.saveContact(contact);
        mailClient.sendMail(contactToMailMapper.mapContactDtoToMail(contact,new Mail(message)));
    }
}


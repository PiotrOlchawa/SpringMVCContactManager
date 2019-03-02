package pl.somehost.contactmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.client.mail.MailClient;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.Mail;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.mapper.ContactToMailMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SimpleMailContactMessageService implements MailContactMessageService {

    @Autowired
    MailClient mailClient;
    @Autowired
    ContactToMailMapper contactToMailMapper;
    @Autowired
    ContactService contactService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailContactMessageService.class);

    @Override
    public void sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        Optional<List<Message>> optionalMessageList = Optional.ofNullable(contact.getMessageList());
        if(!optionalMessageList.isPresent()){
            LOGGER.info("Creating new messageList for contact " + contact.getId());
            contact.setMessageList(new ArrayList<>());
        }
        contact.getMessageList().add(message);
        contactService.saveContact(contact);
        mailClient.sendMail(contactToMailMapper.mapContactDtoToMail(contact,new Mail(message)));
    }
}


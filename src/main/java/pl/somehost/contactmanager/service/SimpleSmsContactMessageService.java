package pl.somehost.contactmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.client.sms.SmsClient;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.SmsMessage;
import pl.somehost.contactmanager.mapper.ContactToSmsMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SimpleSmsContactMessageService implements SmsContactMessageService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailContactMessageService.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ContactToSmsMapper contactToSmsMapper;


    @Override
    public void sendPersistedMessage(Integer contactId, Message message){
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        Optional<List<Message>> optionalMessageList = Optional.ofNullable(contact.getMessageList());
        if(!optionalMessageList.isPresent()){
            LOGGER.info("Creating new messageList for contact " + contact.getId());
            contact.setMessageList(new ArrayList<>());
        }
        contact.getMessageList().add(message);
        contactService.saveContact(contact);
        smsClient.sendMessage(contactToSmsMapper.mapContactToSmsMessage(contact,new SmsMessage(message)));

    }
}

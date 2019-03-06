package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.client.sms.SmsClient;
import pl.somehost.contactmanager.domain.*;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.SmsExceptions;
import pl.somehost.contactmanager.mapper.ContactToSmsMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;
import pl.somehost.contactmanager.service.SimpleMailContactMessageService;

@Service
@Transactional
public class SmsMessageFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailContactMessageService.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ContactToSmsMapper contactToSmsMapper;
    @Autowired
    private MessageSchedulerConfigurator messageSchedulerConfigurator;

    public ContactManagerResponseMessage sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        MessageStatus messageStatus = smsClient
                .sendMessage(contactToSmsMapper.mapContactToSmsMessage(contact, new SmsMessage(message)));
        message.setMessageStatus(messageStatus);
        messageSchedulerConfigurator.configureMessage(message);
        Message persistedMessage = messageService.saveMessage(message);
        if(messageStatus.equals(MessageStatus.NOT_SEND)){
            throw new SmsExceptions("Can't send sms");
        }

        return new ContactManagerResponseMessage("Message to: " + contact.getTelephone() + " was send ");
    }
}

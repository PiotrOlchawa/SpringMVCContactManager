package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.client.sms.SmsClient;
import pl.somehost.contactmanager.domain.*;
import pl.somehost.contactmanager.domain.message.*;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.MessageSendException;
import pl.somehost.contactmanager.mapper.ContactToSmsMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;

@Component
public class SmsMessageFacade implements MessageFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailMessageFacade.class);

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

    @Override
    public ContactManagerResponseMessage sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        message.setMessageSendMethod(MessageSendMethod.MESSAGE_BY_SMS);
        MessageStatus messageStatus = smsClient
                .sendMail(contactToSmsMapper.mapContactToSmsMessage(contact, new SmsMessage(message)));
        message.setMessageStatus(messageStatus);
        messageSchedulerConfigurator.configureMessage(message);
        messageService.saveMessage(message);
        if(messageStatus.equals(MessageStatus.NOT_SEND)){
            throw new MessageSendException("Can't send sms");
        }

        return new ContactManagerResponseMessage("Sms Message to: " + contact.getTelephone() + " was send ");
    }
}
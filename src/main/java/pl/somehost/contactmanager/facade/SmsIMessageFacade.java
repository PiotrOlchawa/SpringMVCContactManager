package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.config.scheduler.MessageSchedulerConfigurator;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.*;
import pl.somehost.contactmanager.domain.message.enums.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;
import pl.somehost.contactmanager.domain.response.CMResponseEntityPreparator;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.MessageSendException;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.messageclient.IMessageClient;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;

@Component
public class SmsIMessageFacade implements IMessageFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailIMessageFacade.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private IMessageClient smsGatewayClient;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private MessageSchedulerConfigurator messageSchedulerConfigurator;
    @Autowired
    private CMResponseEntityPreparator cmResponseEntityPreparator;

    @Override
    public ResponseEntity<ContactManagerResponseMessage> sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        message.setMessageSendMethod(MessageSendMethod.MESSAGE_BY_SMS);
        MessageStatus messageStatus = smsGatewayClient
                .sendMessage(contactMapper.mapContactToSms(contact, new SmsMessage(message)));
        message.setMessageStatus(messageStatus);
        messageSchedulerConfigurator.configureMessage(message);
        Message persistedMessage = messageService.saveMessage(message);
        if (messageStatus.equals(MessageStatus.NOT_SEND)) {
            throw new MessageSendException("Can't send sms");
        }
        return cmResponseEntityPreparator.getResponseEntity("Sms Message to: " + contact.getTelephone() + " was send "
                ,"/message/" + persistedMessage.getId(), HttpStatus.OK);
    }
}

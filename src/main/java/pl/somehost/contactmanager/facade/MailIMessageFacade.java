package pl.somehost.contactmanager.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.config.scheduler.MessageSchedulerConfigurator;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.*;
import pl.somehost.contactmanager.domain.message.definitions.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.definitions.MessageStatus;
import pl.somehost.contactmanager.domain.response.CMResponseEntityProvider;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.MessageSendException;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.messageclient.IMessageClient;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;

@Component
public class MailIMessageFacade implements IMessageFacade {

    private final IMessageClient mailClient;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final MessageService messageService;
    private final MessageSchedulerConfigurator messageSchedulerConfigurator;
    private final CMResponseEntityProvider cmResponseEntityProvider;

    @Autowired
    public MailIMessageFacade(IMessageClient mailClient, ContactMapper contactMapper, ContactService contactService, MessageService messageService, MessageSchedulerConfigurator messageSchedulerConfigurator, CMResponseEntityProvider cmResponseEntityProvider) {
        this.mailClient = mailClient;
        this.contactMapper = contactMapper;
        this.contactService = contactService;
        this.messageService = messageService;
        this.messageSchedulerConfigurator = messageSchedulerConfigurator;
        this.cmResponseEntityProvider = cmResponseEntityProvider;
    }

    @Override
    public ResponseEntity<ContactManagerResponseMessage> sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        message.setMessageSendMethod(MessageSendMethod.MESSAGE_BY_MAIL);
        MessageStatus messageStatus = mailClient.sendMessage(contactMapper.mapContactToMail(contact, new MailMessage(message)));
        message.setMessageStatus(messageStatus);
        messageSchedulerConfigurator.configureMessage(message);
        Message persistedMessage = messageService.saveMessage(message);
        if (messageStatus.equals(MessageStatus.NOT_SEND)) {
            throw new MessageSendException("Can't send mail");
        }
        return cmResponseEntityProvider.getResponseEntity("Mail Message to: " + contact.getTelephone() + " was send "
                , "/message/" + persistedMessage.getId(), HttpStatus.OK);
    }
}


package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.client.mail.MailClient;
import pl.somehost.contactmanager.domain.*;
import pl.somehost.contactmanager.domain.message.*;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.MessageSendException;
import pl.somehost.contactmanager.mapper.ContactToMailMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;

@Component
public class MailMessageFacade implements MessageFacade {

    @Autowired
    private MailClient mailClient;
    @Autowired
    private ContactToMailMapper contactToMailMapper;
    @Autowired
    private ContactService contactService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageSchedulerConfigurator messageSchedulerConfigurator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailMessageFacade.class);

    @Override
    public ContactManagerResponseMessage sendPersistedMessage(Integer contactId, Message message) {
        Contact contact = contactService.getContact(contactId);
        message.setContact(contact);
        message.setMessageSendMethod(MessageSendMethod.MESSAGE_BY_MAIL);
        MessageStatus messageStatus = mailClient.sendMail(contactToMailMapper.mapContactDtoToMail(contact,new MailMessage(message)));
        message.setMessageStatus(messageStatus);
        messageSchedulerConfigurator.configureMessage(message);
        messageService.saveMessage(message);
        if(messageStatus.equals(MessageStatus.NOT_SEND)){
            throw new MessageSendException("Can't send sms");
        }
        return new ContactManagerResponseMessage("Mail Message to: " + contact.getTelephone() + " was send ");
    }
}


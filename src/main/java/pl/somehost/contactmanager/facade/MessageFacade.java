package pl.somehost.contactmanager.facade;

import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;

public interface MessageFacade {

    ContactManagerResponseMessage sendPersistedMessage(Integer messageId, Message message);
}

package pl.somehost.contactmanager.facade;

import org.springframework.http.ResponseEntity;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;

public interface IMessageFacade {

    ResponseEntity<ContactManagerResponseMessage> sendPersistedMessage(Integer messageId, Message message);
}

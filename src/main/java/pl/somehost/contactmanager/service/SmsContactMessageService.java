package pl.somehost.contactmanager.service;

import pl.somehost.contactmanager.domain.Message;

public interface SmsContactMessageService {

    void sendPersistedMessage(Integer contactId, Message message);

}

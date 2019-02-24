package pl.somehost.contactmanager.service;

import pl.somehost.contactmanager.domain.Message;

public interface MailContactMessageService {

    void sendPersistedMessage(Integer messageId, Message message);
}

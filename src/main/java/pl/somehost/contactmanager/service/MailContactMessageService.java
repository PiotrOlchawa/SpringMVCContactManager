package pl.somehost.contactmanager.service;

import pl.somehost.contactmanager.domain.Message;

public interface MailContactMessageService {

    void send(Integer messageId, Message message);
}

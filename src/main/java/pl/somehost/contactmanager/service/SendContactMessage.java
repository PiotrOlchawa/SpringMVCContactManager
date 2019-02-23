package pl.somehost.contactmanager.service;

import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.dto.ContactDto;

public interface SendContactMessage {

    void send(ContactDto contactDto, Message message);
}

package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.client.mail.MailClient;
import pl.somehost.contactmanager.domain.Mail;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.mapper.ContactDtoToMailMapper;

@Service
public class ContactMailService implements SendContactMessage {

    @Autowired
    MailClient mailClient;
    @Autowired
    ContactDtoToMailMapper contactDtoToMailMapper;

    @Override
    public void send(ContactDto contactDto, Message message) {
        mailClient.sendMail(contactDtoToMailMapper.mapContactDtoToMail(contactDto,new Mail(message)));
    }
}


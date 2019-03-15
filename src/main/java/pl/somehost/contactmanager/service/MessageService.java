package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.enums.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;
import pl.somehost.contactmanager.repository.MessageDao;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public Message saveMessage(Message message) {
        return messageDao.save(message);
    }

    public List<Message> getMessageWithStatus(MessageStatus messageStatus) {
        return messageDao.findByMessageStatus(messageStatus);
    }

    public Optional<List<Message>> getMessageWithStatusAndNumberOfTrysGreater(MessageStatus messageStatus, Integer messageTrysCount) {
        return messageDao.findByMessageStatusAndAndSendTraysIsGreaterThan(messageStatus,messageTrysCount);
    }

    public Optional<List<Message>> getStatusAndMessageSendMethodAndSendTraysGreater
            (MessageStatus messageStatus, MessageSendMethod messageSendMethod,Integer messageTrysCount) {
        return messageDao.findByMessageStatusAndMessageSendMethodAndSendTraysGreaterThan(messageStatus,messageSendMethod,messageTrysCount);
    }
}

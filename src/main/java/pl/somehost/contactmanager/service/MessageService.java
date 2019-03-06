package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.MessageStatus;
import pl.somehost.contactmanager.repository.MessageDao;

import java.util.List;

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

    public List<Message> getMessageWithStatusAndNumberOfTrys (MessageStatus messageStatus,Integer messageTrysCount) {
        return messageDao.findByMessageStatusAndAndSendTraysIsGreaterThan(messageStatus,messageTrysCount);
    }
}

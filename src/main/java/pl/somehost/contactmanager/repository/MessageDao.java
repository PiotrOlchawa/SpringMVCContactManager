package pl.somehost.contactmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.definitions.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.definitions.MessageStatus;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MessageDao extends CrudRepository<Message,Integer> {

    List<Message> findByMessageStatus(MessageStatus messageStatus);

    Optional<List<Message>> findByMessageStatusAndAndSendTraysIsGreaterThan(MessageStatus messageStatus, Integer messageTrysCount );

    Optional<List<Message>> findByMessageStatusAndMessageSendMethodAndSendTraysGreaterThan
            (MessageStatus messageStatus, MessageSendMethod messageSendMethod, Integer messageTrysCount);
}
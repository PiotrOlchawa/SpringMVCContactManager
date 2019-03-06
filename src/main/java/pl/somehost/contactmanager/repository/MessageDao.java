package pl.somehost.contactmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.MessageStatus;

import java.util.List;

@Repository
@Transactional
public interface MessageDao extends CrudRepository<Message,Integer> {

    List<Message> findByMessageStatus(MessageStatus messageStatus);

    List<Message> findByMessageStatusAndAndSendTraysIsGreaterThan(MessageStatus messageStatus, Integer messageTrysCount );
}
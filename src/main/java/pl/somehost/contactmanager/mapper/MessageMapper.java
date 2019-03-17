package pl.somehost.contactmanager.mapper;

import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.SmsMessage;

@Component
public class MessageMapper {
    public SmsMessage mapMessageToSmsMessage(Message message){
        return new SmsMessage(message, message.getContact().getTelephone());
    }
}

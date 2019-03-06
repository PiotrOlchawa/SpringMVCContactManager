package pl.somehost.contactmanager.mapper;

import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.SmsMessage;

@Component
public class ContactToSmsMapper {

    public SmsMessage mapContactToSmsMessage(Contact contact,SmsMessage smsMessage){
        smsMessage.setPhoneNumber(contact.getTelephone());
        return smsMessage;
    }

}

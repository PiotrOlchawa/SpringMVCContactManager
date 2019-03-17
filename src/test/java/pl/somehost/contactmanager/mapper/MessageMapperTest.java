package pl.somehost.contactmanager.mapper;

import org.junit.Assert;
import org.junit.Test;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.SmsMessage;

import static org.junit.Assert.*;

public class MessageMapperTest {

    @Test
    public void mapMessageToSmsMessage() {
        //Given
        MessageMapper messageMapper = new MessageMapper();
        Message message = new Message();
        Contact contact = new  Contact.Builder().telephone("123456").build();
        message.setContact(contact);
        message.setMessage("Test Message");
        //When
        SmsMessage smsMessage = messageMapper.mapMessageToSmsMessage(message);
        //Then
        Assert.assertEquals(smsMessage.getPhoneNumber(),"123456");
        Assert.assertEquals(smsMessage.getMessageText(),"Test Message");
    }
}
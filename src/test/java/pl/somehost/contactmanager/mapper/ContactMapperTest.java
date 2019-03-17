package pl.somehost.contactmanager.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.message.MailMessage;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.SmsMessage;
import pl.somehost.contactmanager.domain.security.Authorities;
import pl.somehost.contactmanager.domain.security.LoggedUserGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class ContactMapperTest {

    @Autowired
    @InjectMocks
    private ContactMapper contactMapper;

    @Mock
    private LoggedUserGetter loggedUserGetter;

    @Before
    /* Initialize mocks */
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private final Contact contact = new Contact.Builder()
            .firstName("firstname")
            .lastName("lastname")
            .aptNumber("aptNumber")
            .email("email@email.pl")
            .id(1)
            .streetAdress("streetAdress")
            .telephone("123456789")
            .zipCode("32453")
            .build();

    private final ContactDto contactDto = new ContactDto.Builder()
            .firstName("firstname")
            .lastName("lastname")
            .aptNumber("aptNumber")
            .email("email@email.pl")
            .id(1)
            .streetAdress("streetAdress")
            .telephone("123456789")
            .zipCode("32453")
            .build();

    @Test
    public void shouldMapContactDtoToContact() {
        //Given
        User user = new User(1, "username", "password", new HashSet<Authorities>(), new AdressBook());


        Mockito.when(loggedUserGetter.getLoggedUser()).thenReturn(user);
        //When
        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        //Then
        Assert.assertEquals(contact.getFirstName(), contactDto.getFirstName());
        Assert.assertEquals(contact.getLastName(), contactDto.getLastName());
        Assert.assertEquals(contact.getTelephone(), contactDto.getTelephone());
        Assert.assertEquals(contact.getEmail(), contactDto.getEmail());
        Assert.assertEquals(contact.getAptNumber(), contactDto.getAptNumber());
        Assert.assertEquals(contact.getId(), contactDto.getId());
        Assert.assertEquals(contact.getZipCode(), contactDto.getZipCode());
        Assert.assertEquals(contact.getStreetAdress(), contactDto.getStreetAdress());
    }

    @Test
    public void shouldMapContactListToContactDtoList() {
        //Given
        List<Contact> contacts = new ArrayList<>(Arrays.asList(new Contact[]{contact, contact}));
        Contact modifiedContact = contacts.get(0);
        modifiedContact.setId(2);
        contacts.set(0, modifiedContact);
        //When
        List<ContactDto> contactDtos = contactMapper.mapContactListToContactDtoList(contacts);
        //Then
        Assert.assertEquals(contactDtos.get(0).getId(), 2);
        Assert.assertEquals(contactDtos.size(), 2);
        Assert.assertEquals(contactDtos.get(1).getEmail(), "email@email.pl");
    }

    @Test
    public void shouldMapContactToContactDto() {
        //Given
        User user = new User(1, "username", "password", new HashSet<Authorities>(), new AdressBook());


        Mockito.when(loggedUserGetter.getLoggedUser()).thenReturn(user);
        //When
        ContactDto contactDto = contactMapper.mapContactToContactDto(contact);
        //Then
        Assert.assertEquals(contact.getFirstName(), contactDto.getFirstName());
        Assert.assertEquals(contact.getLastName(), contactDto.getLastName());
        Assert.assertEquals(contact.getTelephone(), contactDto.getTelephone());
        Assert.assertEquals(contact.getEmail(), contactDto.getEmail());
        Assert.assertEquals(contact.getAptNumber(), contactDto.getAptNumber());
        Assert.assertEquals(contact.getId(), contactDto.getId());
        Assert.assertEquals(contact.getZipCode(), contactDto.getZipCode());
        Assert.assertEquals(contact.getStreetAdress(), contactDto.getStreetAdress());
    }

    @Test
    public void shouldMapContactToMail() {
        //Given
        Message message = new Message();
        message.setMessage("New Mail Message");
        MailMessage mailMessage = new MailMessage(message);
        // When
        MailMessage mappedMailMessage = contactMapper.mapContactToMail(contact, mailMessage);
        // Then
        Assert.assertEquals(mappedMailMessage.getMessage(), "New Mail Message");
        Assert.assertEquals(mappedMailMessage.getMailTo(), "email@email.pl");
    }

    @Test
    public void shouldMapContactToSms() {
        //Given
        Message message = new Message();
        message.setMessage("New Mail Message");
        SmsMessage smsMessage = new SmsMessage(message);
        //When
        SmsMessage mappedSmsMessage = contactMapper.mapContactToSms(contact, smsMessage);
        //Then
        Assert.assertEquals(mappedSmsMessage.getMessageText(), "New Mail Message");
        Assert.assertEquals(mappedSmsMessage.getPhoneNumber(), "123456789");
    }
}
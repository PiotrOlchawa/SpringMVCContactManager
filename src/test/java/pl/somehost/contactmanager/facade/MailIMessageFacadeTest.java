package pl.somehost.contactmanager.facade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.MessageService;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class MailIMessageFacadeTest {

    @Autowired
    @InjectMocks
    MailIMessageFacade mailIMessageFacade;

    @Mock
    private ContactService contactService;
    @Mock
    private MessageService messageService;

    @Before
    /* Initialize mocks */
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendPersistedMessage() {
        //Given
        Contact contact = new Contact.Builder().email("piotr@somehost.pl").build();
        Message message = new Message();
        message.setMessage("Test Message");
        message.setId(1);
        Mockito.when(contactService.getContact(Mockito.anyInt())).thenReturn(contact);
        Mockito.when(messageService.saveMessage(message)).thenReturn(message);
        //When
        ResponseEntity<ContactManagerResponseMessage> contactManagerResponseMessageResponseEntity
                = mailIMessageFacade.sendPersistedMessage(1,  message);
        HttpStatus httpResponseStatusCode = contactManagerResponseMessageResponseEntity.getStatusCode();
        Integer responseEntityBodySize = contactManagerResponseMessageResponseEntity.getBody().getMessage().length();
        //Then
        Assert.assertEquals(HttpStatus.OK,httpResponseStatusCode);
        Assert.assertTrue(responseEntityBodySize >0);
        Assert.assertTrue(contactManagerResponseMessageResponseEntity.hasBody());


    }
}
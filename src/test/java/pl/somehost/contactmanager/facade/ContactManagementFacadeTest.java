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
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.service.ContactService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
public class ContactManagementFacadeTest {

    @Autowired
    @InjectMocks
    ContactManagementFacade contactManagementFacade;
    @Mock
    private ContactService contactService;
    @Mock
    private ContactMapper contactMapper;

    @Before
    /* Initialize mocks */
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    public void shouldCreateContact() {
        //Given
        ContactDto contactDto = new ContactDto();
        contactDto.setId(1);
        Contact contact = new Contact();
        contact.setId(1);
        Mockito.when(contactMapper.mapContactDtoToContact(contactDto)).thenReturn(contact);
        Mockito.when(contactService.saveContact(contact)).thenReturn(contact);
        //When
        ResponseEntity<ContactManagerResponseMessage> contactManagerResponseMessage = contactManagementFacade.createContact(contactDto);
        HttpStatus httpResponseStatus = contactManagerResponseMessage.getStatusCode();
        //Then
        Assert.assertEquals(HttpStatus.CREATED, httpResponseStatus);

    }
}
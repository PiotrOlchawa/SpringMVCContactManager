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
import pl.somehost.contactmanager.domain.security.Authorities;
import pl.somehost.contactmanager.domain.security.LoggedUserGetter;

import java.util.HashSet;

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

    @Test
    public void shouldMapContactDtoMapToContact() {
        //Given
        User user = new User(1, "username", "password", new HashSet<Authorities>(), new AdressBook());

        ContactDto contactDto = new ContactDto.Builder()
                .firstName("firstname")
                .lastName("lastname")
                .aptNumber("aptNumber")
                .email("email@email.pl")
                .id(1)
                .streetAdress("streetAdress")
                .telephone("123456789")
                .zipCode("32453")
                .build();
        Mockito.when(loggedUserGetter.getLoggedUser()).thenReturn(user);
        //When
        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        //Then
        Assert.assertEquals(contact.getFirstName(),contactDto.getFirstName());
        Assert.assertEquals(contact.getLastName(),contactDto.getLastName());
        Assert.assertEquals(contact.getTelephone(),contactDto.getTelephone());
        Assert.assertEquals(contact.getEmail(),contactDto.getEmail());
        Assert.assertEquals(contact.getAptNumber(),contactDto.getAptNumber());
        Assert.assertEquals(contact.getId(),contactDto.getId());
        Assert.assertEquals(contact.getZipCode(),contactDto.getZipCode());
        Assert.assertEquals(contact.getStreetAdress(),contactDto.getStreetAdress());
    }

    @Test
    public void mapContactListToContactDtoList() {
    }

    @Test
    public void mapContactToContactDto() {
    }

    @Test
    public void mapContactToMail() {
    }

    @Test
    public void mapContactToSms() {
    }

    @Test
    public void mapContactToSms1() {
    }
}
package pl.somehost.contactmanager.facade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestBeanConfig;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.repository.ContactDao;
import pl.somehost.contactmanager.repository.UserDao;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class ContactManagementFacadeTest {

    @Autowired
    UserDao userDao;

    @Autowired
    ContactDao contactDao;

    @Autowired
    ContactManagementFacade contactManagementFacade;

    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN", "ROLE_USER" })
    public void shouldReturnContactForUser() {
        //Given

        User user = new User();
        AdressBook adressBook = new AdressBook();
        Contact contact = new Contact();
        contact.setAdressBook(adressBook);
        user.setAdressBook(adressBook);
        contact.setFirstName("testowy");
/*
        //When
        User persistedUser = userDao.save(user);
        Contact persistedContactDto = contactDao.findByAdressBook_Id(persistedUser.getAdressBook().getId()).get();
        Integer userId = persistedUser.getId();
        ContactDto contactDto = contactManagementFacade.getContactsForUser(userId);
        //Then
        Assert.assertEquals(contactDto.getFirstName(),persistedContactDto.getFirstName());
*/

    }
}
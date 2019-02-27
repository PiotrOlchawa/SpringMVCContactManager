package pl.somehost.contactmanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.Message;

import javax.transaction.Transactional;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
@Transactional
public class ContactMailServiceTest {

    @Autowired
    private MailContactMessageService contactMailService;
    @Autowired
    private ContactService contactService;

    @Test
    public void shouldSendEmail() {
        Contact contact = new Contact();
        contact.setEmail("piotr.olchawa@protonmail.ch");
        Contact persistedContact = contactService.saveContact(contact);
        Message message = new Message();
        message.setMessage("Test message");
        contactMailService.sendPersistedMessage(persistedContact.getId(),message);
    }
}
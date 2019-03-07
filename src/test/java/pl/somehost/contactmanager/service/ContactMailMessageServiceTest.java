package pl.somehost.contactmanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.facade.MessageFacade;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
@Transactional
public class ContactMailMessageServiceTest {

    @Autowired
    private MessageFacade mailMessageFacade; // Autowired by name
    @Autowired
    private ContactService contactService;

    @Test
    public void shouldSendEmail() {
        Contact contact = new Contact();
        contact.setEmail("piotr.olchawa@protonmail.ch");
        Contact persistedContact = contactService.saveContact(contact);
        Message message = new Message();
        message.setMessage("Test message");
        mailMessageFacade.sendPersistedMessage(persistedContact.getId(),message);
    }
}
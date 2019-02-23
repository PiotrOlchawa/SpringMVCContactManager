package pl.somehost.contactmanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestBeanConfig;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.dto.ContactDto;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class ContactMailServiceTest {

    @Autowired
    ContactMailService contactMailService;

    @Test
    public void shouldSendEmail() {
        ContactDto contactDto = new ContactDto();
        contactDto.setEmail("piotr.olchawa@protonmail.ch");
        Message message = new Message();
        message.setMessage("Test message");
        contactMailService.send(contactDto,message);
    }
}
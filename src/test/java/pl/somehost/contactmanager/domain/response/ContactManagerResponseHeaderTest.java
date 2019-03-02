package pl.somehost.contactmanager.domain.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestBeanConfig;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class ContactManagerResponseHeaderTest {

    @Test
    public void getResponseHeaders() {
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "User with id: " + 10 + " was deleted ");
        HttpHeaders httpHeaders = contactManagerResponseHeader.getResponseHeaders();
    }
}
package pl.somehost.contactmanager.domain.response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.test.TestingBeanConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
public class ContactManagerResponseHeaderTest {

    @Test
    public void getResponseHeaders() {
        //Given
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "User with id: " + 10 + " was deleted");
        // When
        HttpHeaders httpHeaders = contactManagerResponseHeader.getResponseHeaders();
        // Then
        Assert.assertEquals("User with id: 10 was deleted",httpHeaders.get("ContactResponceHeader").get(0));
    }
}
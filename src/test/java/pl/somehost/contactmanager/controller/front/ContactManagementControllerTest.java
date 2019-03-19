package pl.somehost.contactmanager.controller.front;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestingBeanConfig.class})
//@WithMockUser(username = "admin" , authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class ContactManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    // This is Mock - injected without AOP, initialized as bean through @ContextConfiguration(classes = {TestingBeanConfig.class})
    // as @Primary instead Autowiring with Qualifier that does not working /why?/. Easier alternative is use MockBean but not mixing with springboot.
    // Have two beans of type ContactManagementFacade in context scanned with @ComponentScan with TestingBeanConfig.class.
    private ContactManagementFacade contactManagementFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementControllerTest.class);

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

    @Before
    /* Initialize mocks */
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName="userDetailsServiceImpl", value = "piotr")
    //This is needed because controller have @AuthenticationPrincipal; userDetailsServiceImpl need real database with specified user
    public void shouldReturnAllContacts() {

        // Given
        List<ContactDto> contactDtos = new ArrayList<>(Arrays.asList(contactDto,contactDto));
        List<Contact> contacts = new ArrayList<>(Arrays.asList(contact,contact));
        ResponseEntity<List<ContactDto>> listResponseEntity = new ResponseEntity<>(contactDtos, HttpStatus.OK);
        //when&then
        when(contactManagementFacade.getContactsForUser(any())).thenReturn(listResponseEntity);
        try {
            mockMvc.perform(get("/front-user/contact").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$", hasSize(2)));
            mockMvc.perform(get("/front-user/contact").contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContact() {
    }

    @Test
    public void createContact() {
    }

    @Test
    public void updateContact() {
    }

    @Test
    public void deleteContact() {
    }
}
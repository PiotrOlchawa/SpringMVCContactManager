package pl.somehost.contactmanager.controller.front;

import com.google.gson.Gson;
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
import pl.somehost.contactmanager.config.test.TestingBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.response.CMResponseEntityProvider;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestingBeanConfig.class})
public class ContactManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    // This is Mock - injected without AOP, initialized as @Primary bean through @ContextConfiguration(classes = {TestingBeanConfig.class})
    // as @Primary instead Autowiring with Qualifier that does not working /why?/. Easier alternative is use MockBean but not mixing with springboot.
    // Have two beans of type ContactManagementFacade in context scanned with @ComponentScan with TestingBeanConfig.class.
    private ContactManagementFacade contactManagementFacade;

    @Autowired
    private CMResponseEntityProvider cmResponseEntityProvider;


    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementControllerTest.class);


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
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsServiceImpl", value = "test")
    public void shouldReturnAllContacts() throws Exception {

        // Given

        Contact contact = new Contact.Builder()
                .firstName("firstname")
                .lastName("lastname")
                .aptNumber("aptNumber")
                .email("email@email.pl")
                .id(1)
                .streetAdress("streetAdress")
                .telephone("123456789")
                .zipCode("32453")
                .build();

        List<ContactDto> contactDtos = new ArrayList<>(Arrays.asList(contactDto, contactDto));
        List<Contact> contacts = new ArrayList<>(Arrays.asList(contact, contact));
        ResponseEntity<List<ContactDto>> listResponseEntity = new ResponseEntity<>(contactDtos, HttpStatus.OK);

        //When & Then
        when(contactManagementFacade.getContactsForUser(any())).thenReturn(listResponseEntity);
        mockMvc.perform(get("/front-user/contact").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsServiceImpl", value = "test")
    public void shouldCreateContact() throws Exception {

        //Given
        Gson gson = new Gson();
        String contactDtoJson = gson.toJson(contactDto);

        ResponseEntity<ContactManagerResponseMessage> responseEntity = cmResponseEntityProvider.getResponseEntity("Contact with id " + contactDto.getId() + " was created"
                , "/contact/" + contactDto.getId(), HttpStatus.CREATED);
        LOGGER.info(responseEntity.getStatusCode().toString());

        //When & Then
        when(contactManagementFacade.createContact(any())).thenReturn(responseEntity);

        mockMvc.perform(post("/front-user/contact").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(contactDtoJson))
                .andExpect(status().isCreated())
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.message", is("Contact with id " + "1" + " was created")));
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsServiceImpl", value = "test")
    public void shouldUpdateContact() throws Exception {

        //Given
        Gson gson = new Gson();
        String contactDtoJson = gson.toJson(contactDto);

        ResponseEntity<ContactManagerResponseMessage> responseEntity = cmResponseEntityProvider.getResponseEntity("Contact with id " + contactDto.getId() + " was updated"
                , "/contact/" + contactDto.getId(), HttpStatus.OK);
        LOGGER.info(responseEntity.getStatusCode().toString());

        //When & Then
        when(contactManagementFacade.updateContactForUser(any(), any())).thenReturn(responseEntity);

        mockMvc.perform(put("/front-user/contact").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(contactDtoJson))
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Contact with id " + "1" + " was updated")));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsServiceImpl", value = "test")
    public void deleteContact() throws Exception {
        //Given
        ResponseEntity<ContactManagerResponseMessage> responseEntity = cmResponseEntityProvider.getResponseEntity("Contact with id " + contactDto.getId() + " was deleted"
                , "/contact/" + contactDto.getId(), HttpStatus.OK);
        LOGGER.info(responseEntity.getStatusCode().toString());
        //When & Then
        when(contactManagementFacade.deleteContactForCurrentUser(any(), any())).thenReturn(responseEntity);

        mockMvc.perform(delete("/front-user/contact").contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andDo(result -> LOGGER.info(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Contact with id " + "1" + " was deleted")));
    }
}
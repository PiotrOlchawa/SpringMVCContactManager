package pl.somehost.contactmanager.controller.front;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.security.LoggedUserGetter;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestingBeanConfig.class})
@WithMockUser(username = "user", authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class ContactManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoggedUserGetter loggedUserGetter;

    @Mock
    private ContactManagementFacade contactManagementFacade;

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
    public void shouldReturnAllContacts() {

        // Given
        User user = new User();
        user.setUsername("user");

        List<ContactDto> contactDtos = new ArrayList<>(Arrays.asList(contactDto,contactDto));
        ResponseEntity<List<ContactDto>> listResponseEntity = new ResponseEntity<>(contactDtos, HttpStatus.OK);
        //when&then
        //when(contactManagementFacade.getContactsForUser(user)).thenReturn(listResponseEntity);
        try {
            mockMvc.perform(get("/contact").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$", hasSize(2)));
           /* mockMvc.perform(get("/contact").contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> System.out.println(result.getResponse().getContentAsString()));*/

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
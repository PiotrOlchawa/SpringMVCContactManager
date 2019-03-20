package pl.somehost.contactmanager.config.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import static org.mockito.Mockito.mock;

@ComponentScan(basePackages = "pl.somehost.contactmanager")
public class TestingBeanConfig {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    public MockMvc mockMvc(){
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Bean
    @Primary
    public ContactManagementFacade mockContactManagementFacade() {
        return mock(ContactManagementFacade.class);
    }

    @Bean
    @Primary
    public UserDetailsService testUserDetailsServiceImpl(){
        return new TestUserDetailsServiceImpl();
    }

}
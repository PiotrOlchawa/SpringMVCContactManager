package pl.somehost.contactmanager.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.somehost.contactmanager.config.TestBeanConfig;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.domain.Roles;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.facade.UserManagementFacade;

import java.util.HashSet;
import java.util.Set;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})

public class UserMangementFacadeTest {

    @Autowired
    UserManagementFacade userMangementFacade;

    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN", "ROLE_USER" })
    public void shouldUserDtoToUserMapperPersistUser() {

        //Given
        UserDto facadeUserDto = new UserDto();
        Set<Authorities> authoritiesList = new HashSet<>();
        facadeUserDto.setUsername("Test");
        facadeUserDto.setPassword("Test");
        authoritiesList.add(new Authorities(Roles.ROLE_DBA));
        facadeUserDto.setAuthorities(authoritiesList);
        //When
        User user = userMangementFacade.createUser(facadeUserDto);
        //Then
        System.out.println("FacadeUser---" +facadeUserDto);
        System.out.println("User---" + user);
        Assert.assertEquals(user.getUsername(),facadeUserDto.getUsername());
    }
}
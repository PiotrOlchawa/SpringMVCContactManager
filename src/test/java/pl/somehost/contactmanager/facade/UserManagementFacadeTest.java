package pl.somehost.contactmanager.facade;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.config.TestingBeanConfig;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.domain.Roles;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.repository.UserDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingBeanConfig.class})
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
@Transactional
public class UserManagementFacadeTest {

    @Autowired
    UserManagementFacade userMangementFacade;
    @Autowired
    PasswordEncoder passwordsEncoder;
    @Autowired
    UserDao userDao;

    @Test
    public void shouldUserManagementFacadeCreateUser() {

        //Given
        Set<Authorities> authoritiesList = new HashSet<>();
        authoritiesList.add(new Authorities(Roles.ROLE_DBA));
        authoritiesList.add(new Authorities(Roles.ROLE_ADMIN));
        UserDto facadeUserDto = new UserDto();
        facadeUserDto.setUsername("Test");
        facadeUserDto.setPassword("Test");
        facadeUserDto.setId(1);
        facadeUserDto.setAdresBook(new AdressBook());
        facadeUserDto.setPassword("password");
        facadeUserDto.setAuthorities(authoritiesList);
        //When
        ResponseEntity<ContactManagerResponseMessage> contactManagerResponseMessageResponseEntity = userMangementFacade.createUser(facadeUserDto);
        String [] response = contactManagerResponseMessageResponseEntity.getBody().getMessage().split(": ");
        User user = userDao.findById(Integer.valueOf(response[2])).get();
        List<String> userAuthorities = user.getAuthorities().stream().map(authorities -> authorities.getAuthority())
                .collect(Collectors.toList());
        List<String> userDtoAuthorities = facadeUserDto.getAuthorities().stream().map(authorities -> authorities.getAuthority())
                .collect(Collectors.toList());

        //Then
        Assert.assertEquals(user.getId(), facadeUserDto.getId());
        Assert.assertEquals(user.getUsername(), facadeUserDto.getUsername());
        Assert.assertTrue(passwordsEncoder.matches(facadeUserDto.getPassword(), user.getPassword()));
        Assert.assertTrue(userAuthorities.containsAll(userDtoAuthorities));
        Assert.assertNotNull(user.getAdressBook());
    }

    @Test
    public void shouldUserManagementFacadeModifyUser() {
        //Given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        User persistedUser = userDao.save(user);
        UserDto userDto = new UserDto();
        userDto.setUsername("username1");
        userDto.setPassword("password1");
        userDto.setAuthorities(new HashSet<>());
        System.out.println("persistedUser.getId()  " +persistedUser.getId());
        userDto.setId(persistedUser.getId());
        //When
        ResponseEntity<ContactManagerResponseMessage> contactManagerResponseMessageResponseEntity = userMangementFacade.modifyUser(userDto);
        String [] response = contactManagerResponseMessageResponseEntity.getBody().getMessage().split(": ");
        User modifiedUser = userDao.findById(Integer.valueOf(response[2])).get();
        //Then
        Assert.assertEquals(modifiedUser.getUsername(),"username1");
        Assert.assertTrue(passwordsEncoder.matches("password1",modifiedUser.getPassword()));
        Assert.assertEquals(modifiedUser.getId(),persistedUser.getId());
    }

}

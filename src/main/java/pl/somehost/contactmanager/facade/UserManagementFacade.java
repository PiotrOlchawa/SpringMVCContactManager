package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.FacadeUserDto;
import pl.somehost.contactmanager.mapper.FacadeUserDtoToUserMapper;
import pl.somehost.contactmanager.service.AuthoritiesService;
import pl.somehost.contactmanager.service.UserService;

@Component
public class UserManagementFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserManagementFacade.class);

    @Autowired
    UserService userService;
    @Autowired
    AuthoritiesService authoritiesService;
    @Autowired
    FacadeUserDtoToUserMapper facadeUserDtoToUserMapper;
    @Autowired
    private ApplicationContext appContext;

    public User createUser(FacadeUserDto facadeUserDto) {

        LOGGER.info("createUser CALL");

        PasswordEncoder passwordEncoder = (PasswordEncoder)appContext.getBean("passwordEncoder");
        facadeUserDto.setPassword(passwordEncoder.encode(facadeUserDto.getPassword()));
        facadeUserDto.setAdresBook(new AdressBook());
        User user = facadeUserDtoToUserMapper.mapFacadeUserDtoToUser(facadeUserDto);
        user.getAuthorities().stream().forEach(l->l.setUser(user));
        User persistedUser = userService.save(user);
        return persistedUser;
    }

    public void deleteUser(Integer userId) {
        userService.deleteUser(userId);
    }
}

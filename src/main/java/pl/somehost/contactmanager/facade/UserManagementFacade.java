package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.PasswordsEncoder;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.FacadeUserDto;
import pl.somehost.contactmanager.mapper.FacadeUserDtoToUserMapper;
import pl.somehost.contactmanager.service.AuthoritiesService;
import pl.somehost.contactmanager.service.UserService;

import java.util.Optional;

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
    private PasswordsEncoder passwordsEncoder;

    public User createUser(FacadeUserDto facadeUserDto) {

        LOGGER.info("createUser CALL");
        facadeUserDto.setPassword(passwordsEncoder.encode(facadeUserDto.getPassword()));
        facadeUserDto.setAdresBook(new AdressBook());
        User user = facadeUserDtoToUserMapper.mapFacadeUserDtoToUser(facadeUserDto);
        user.getAuthorities().stream().forEach(l->l.setUser(user));
        User persistedUser = userService.save(user);
        return persistedUser;
    }

    public void deleteUser(Integer userId)
    {
        LOGGER.info("deleteUser CALL");
        userService.deleteUser(userId);
    }

    public void modifyUser(FacadeUserDto facadeUserDto) {
        LOGGER.info("modifyUser CALL");
        Optional<User> optionalCurrentUser = userService.getUser(facadeUserDto.getId());
        if(optionalCurrentUser.isPresent()){
            User currentUser = optionalCurrentUser.get();
            currentUser.setUsername(facadeUserDto.getUsername());
            currentUser.setPassword(passwordsEncoder.encode(facadeUserDto.getPassword()));
            facadeUserDto.getAuthorities().stream().forEach(l->l.setUser(currentUser));
            currentUser.setAuthorities(facadeUserDto.getAuthorities());
            userService.deleteUser(currentUser.getId());
            userService.save(currentUser);
        } else {
            LOGGER.info("No USER ! facadeUserDto.getID() " + facadeUserDto.getId());
        }

    }
}

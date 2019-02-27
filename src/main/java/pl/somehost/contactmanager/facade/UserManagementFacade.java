package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.mapper.UserMapper;
import pl.somehost.contactmanager.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserManagementFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserManagementFacade.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoggedUserGetter loggedUserGetter;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    //@AuthenticationPrincipal
    public User createUser(UserDto userDto) {
        LOGGER.info("createUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        User user = userMapper.mapUserDtoToUser(userDto);
        return userService.save(user);
    }

    public void deleteUser(Integer userId) {
        LOGGER.info("deleteUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        userService.deleteUser(userId);
    }

    public User modifyUser(UserDto userDto) {
        LOGGER.info("modifyUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        Optional<User> optionalCurrentUser = userService.getUser(userDto.getId());
        if (optionalCurrentUser.isPresent()) {
            userDto.setAdresBook(optionalCurrentUser.get().getAdressBook());
            User user = userMapper.mapUserDtoToUser(userDto);
            LOGGER.info(userDto.getAuthorities() == null ? "Authorities are null"
                    : "Authorities while mapping for userId=" + userDto.getId() + " "
                    + userDto.getAuthorities().stream().map(l -> l.getAuthority()).collect(Collectors.joining(roleJoiningCharacter)));
            LOGGER.info("Persisted User Authorities for userId,userName " + user.getId() + "," + user.getUsername() + " "
                    + user.getAuthorities().stream().map(Authorities::getAuthority).collect(Collectors.joining(roleJoiningCharacter)));
            return userService.save(user);
        } else {
            LOGGER.info("User does not exist ! id: userDto.getID() " + userDto.getId());
            return new User();
        }
    }
}

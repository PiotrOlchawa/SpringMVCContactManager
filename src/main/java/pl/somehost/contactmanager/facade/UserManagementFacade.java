package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.security.Authorities;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.domain.response.CMResponseEntityProvider;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.UserNotFoundException;
import pl.somehost.contactmanager.mapper.UserMapper;
import pl.somehost.contactmanager.service.ResourceLocationService;
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
    private ContactManagerResponseMessage contactManagerResponseMessage;
    @Autowired
    private ResourceLocationService resourceLocationService;
    @Autowired
    private CMResponseEntityProvider cmResponseEntityProvider;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    public ResponseEntity<ContactManagerResponseMessage> createUser(UserDto userDto) {

        User user = userMapper.mapUserDtoToUser(userDto);
        User persistedUser = userService.save(user);
        contactManagerResponseMessage.setMessage("User was created: user id: " + persistedUser.getId());
        return cmResponseEntityProvider.getResponseEntity("User with id " + persistedUser.getId() + " was created"
                , "/user/" + persistedUser.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<ContactManagerResponseMessage> deleteUser(Integer userId, User authenticatedUser) {

        userService.deleteUser(userId);
        contactManagerResponseMessage.setMessage("User was deleted: user id: " + userId);
        return cmResponseEntityProvider.getResponseEntity("User with id " + userId + " was deleted", HttpStatus.OK);
    }

    public ResponseEntity<ContactManagerResponseMessage> modifyUser(UserDto userDto) {

        Optional<User> optionalCurrentUser = userService.getUser(userDto.getId());
        Optional<User> optionalUser = userService.getUser(userDto.getId());
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with id: " + userDto.getId() + " was not found"));

        userDto.setAdresBook(optionalCurrentUser.get().getAdressBook());
        User user = userMapper.mapUserDtoToUser(userDto);
        LOGGER.info(userDto.getAuthorities() == null ? "Authorities are null"
                : "Authorities while mapping for userId=" + userDto.getId() + " "
                + userDto.getAuthorities().stream().map(Authorities::getAuthority).collect(Collectors.joining(roleJoiningCharacter)));
        LOGGER.info("Persisted User Authorities for userId,userName " + user.getId() + "," + user.getUsername() + " "
                + user.getAuthorities().stream().map(Authorities::getAuthority).collect(Collectors.joining(roleJoiningCharacter)));
        userService.save(user);

        return cmResponseEntityProvider.getResponseEntity("User with id " + userDto.getId() + " was modified"
                , "/user/" + userDto.getId(), HttpStatus.OK);
    }
}

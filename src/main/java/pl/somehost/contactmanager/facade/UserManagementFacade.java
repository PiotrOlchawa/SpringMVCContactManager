package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseHeader;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.UserNotFoundException;
import pl.somehost.contactmanager.mapper.UserMapper;
import pl.somehost.contactmanager.service.ResourceLocationService;
import pl.somehost.contactmanager.service.UserService;

import java.net.URI;
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
    @Autowired
    private ContactManagerResponseMessage contactManagerResponseMessage;
    @Autowired
    private ResourceLocationService resourceLocationService;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;


    //@AuthenticationPrincipal
    public ResponseEntity<ContactManagerResponseMessage> createUser(UserDto userDto) {

        LOGGER.info("createUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        User user = userMapper.mapUserDtoToUser(userDto);
        User persistedUser = userService.save(user);
        contactManagerResponseMessage.setMessage("User was created: user id: " + persistedUser.getId());
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation("/user/" + persistedUser.getId());
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "Link to created resource", resourceLocation);
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<ContactManagerResponseMessage> deleteUser(Integer userId) {

        LOGGER.info("deleteUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        userService.deleteUser(userId);
        contactManagerResponseMessage.setMessage("User was deleted: user id: " + userId);
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "User with id: " + userId + " was deleted ");
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<ContactManagerResponseMessage> modifyUser(UserDto userDto) {

        LOGGER.info("modifyUser CALL" + " by " + loggedUserGetter.getLoggedUserName());
        Optional<User> optionalCurrentUser = userService.getUser(userDto.getId());
        Optional<User> optionalUser = userService.getUser(userDto.getId());
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with id: " + userDto.getId() + " was not found"));

        userDto.setAdresBook(optionalCurrentUser.get().getAdressBook());
        User user = userMapper.mapUserDtoToUser(userDto);
        LOGGER.info(userDto.getAuthorities() == null ? "Authorities are null"
                : "Authorities while mapping for userId=" + userDto.getId() + " "
                + userDto.getAuthorities().stream().map(l -> l.getAuthority()).collect(Collectors.joining(roleJoiningCharacter)));
        LOGGER.info("Persisted User Authorities for userId,userName " + user.getId() + "," + user.getUsername() + " "
                + user.getAuthorities().stream().map(Authorities::getAuthority).collect(Collectors.joining(roleJoiningCharacter)));
        userService.save(user);

        contactManagerResponseMessage.setMessage("User was modified: user id: " + userDto.getId());
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation("/user/" + userDto.getId());
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "Link to updated resource", resourceLocation);
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), HttpStatus.OK);
    }
}

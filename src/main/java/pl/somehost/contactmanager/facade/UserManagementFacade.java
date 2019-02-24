package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.mapper.UserMapper;
import pl.somehost.contactmanager.service.AuthoritiesService;
import pl.somehost.contactmanager.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserManagementFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserManagementFacade.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private UserMapper userMapper;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    //@AuthenticationPrincipal

    public User createUser(UserDto userDto) {

        LOGGER.info("createUser CALL");
        User user = userMapper.mapNewUserDtoToUser(userDto);
        return userService.save(user);
    }

    public void deleteUser(Integer userId)
    {
        LOGGER.info("deleteUser CALL");
        userService.deleteUser(userId);
    }

    public User modifyUser(UserDto userDto) {
        LOGGER.info("modifyUser CALL");
        Optional<User> optionalCurrentUser = userService.getUser(userDto.getId());
        if(optionalCurrentUser.isPresent()){
            User user = userMapper.mapExistedUserDtoToUser(userDto,optionalCurrentUser.get());
            LOGGER.info("Persisted User Authorities for userId,userName " + user.getId()+ "," + user.getUsername() + " "
                    + user.getAuthorities().stream().map(Authorities::getAuthority).collect(Collectors.joining(roleJoiningCharacter)));
            return userService.save(user);
        } else {
            LOGGER.info("No USER ! userDto.getID() " + userDto.getId());
            return new User();
        }

    }
}

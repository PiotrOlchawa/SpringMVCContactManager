package pl.somehost.contactmanager.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.mapper.UserDtoToUserMapper;
import pl.somehost.contactmanager.service.AuthoritiesService;
import pl.somehost.contactmanager.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserManagementFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserManagementFacade.class);

    @Autowired
    UserService userService;
    @Autowired
    AuthoritiesService authoritiesService;
    @Autowired
    UserDtoToUserMapper userDtoToUserMapper;

    public User createUser(UserDto userDto) {

        LOGGER.info("createUser CALL");
        User user = userDtoToUserMapper.mapUserDtoToUserWhileCreatingNew(userDto);
        return userService.save(user);

    }

    public void deleteUser(Integer userId)
    {
        LOGGER.info("deleteUser CALL");
        userService.deleteUser(userId);
    }

    public void modifyUser(UserDto userDto) {
        LOGGER.info("modifyUser CALL");

        Optional<User> optionalCurrentUser = userService.getUser(userDto.getId());
        if(optionalCurrentUser.isPresent()){
            User user = userDtoToUserMapper.mapUserDtoToUserWhileModyfing(userDto);
            LOGGER.info("Persisted User Authorities for userId,userName " + user.getId()+ "," + user.getUsername() + " "
                    + user.getAuthorities().stream().map(l->l.getAuthority()).collect(Collectors.joining(",")));
            userService.save(user);
        } else {
            LOGGER.info("No USER ! userDto.getID() " + userDto.getId());
        }

    }
}
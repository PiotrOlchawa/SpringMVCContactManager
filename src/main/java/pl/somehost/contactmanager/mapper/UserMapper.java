package pl.somehost.contactmanager.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.PasswordsEncoder;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    PasswordsEncoder passwordsEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

    public User mapUserDtoToUserWhileCreatingNew(UserDto userDto) {
        if(userDto.getAdresBook() == null){
            userDto.setAdresBook(new AdressBook());
        }
        return createUserFromUserDto(userDto);
    }

    public User mapUserDtoToUserWhileModyfing(UserDto userDto,User user) {
        userDto.setAdresBook(user.getAdressBook());
        LOGGER.info("Authorities while mapping for userId=" +userDto.getId()+ " "
                + userDto.getAuthorities().stream().map(l->l.getAuthority()).collect(Collectors.joining(",")));
        return createUserFromUserDto(userDto);
    }

    private User createUserFromUserDto(UserDto userDto){
        User user = new User(userDto.getId(),userDto.getUsername()
                , passwordsEncoder.encode(userDto.getPassword())
                , userDto.getAuthorities()
                , userDto.getAdresBook());

        user.getAuthorities().stream().forEach(l->l.setUser(user));
        LOGGER.info("Authorities while returning user to persist for userId= " + user.getId()+ " "
                + user.getAuthorities().stream().map(l->l.getAuthority()).collect(Collectors.joining(",")));
        return user;
    }
}

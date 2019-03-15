package pl.somehost.contactmanager.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.security.PasswordsEncoder;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private PasswordsEncoder passwordsEncoder;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

    public User mapUserDtoToUser(UserDto userDto) {
        if(userDto.getAdresBook() == null){
            userDto.setAdresBook(new AdressBook());
        }
        User user = new User(userDto.getId(),userDto.getUsername()
                , passwordsEncoder.encode(userDto.getPassword())
                , userDto.getAuthorities()
                , userDto.getAdresBook());

        user.getAuthorities().stream().forEach(l->l.setUser(user));
        LOGGER.info( userDto.getAuthorities() == null ? "Authorities are null"
                : "Authorities while returning user to persist for userId= " +userDto.getId()+ " "
                + userDto.getAuthorities().stream().map(l->l.getAuthority()).collect(Collectors.joining(roleJoiningCharacter)));
        return user;
    }
}

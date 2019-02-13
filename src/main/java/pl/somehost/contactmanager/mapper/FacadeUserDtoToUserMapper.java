package pl.somehost.contactmanager.mapper;

import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.FacadeUserDto;

@Component
public class FacadeUserDtoToUserMapper {

    public User mapFacadeUserDtoToUser(FacadeUserDto facadeUserDto) {
        return new User(facadeUserDto.getUsername()
                ,facadeUserDto.getPassword(),facadeUserDto.getAuthorities(),facadeUserDto.getAdresBook());
    }
}

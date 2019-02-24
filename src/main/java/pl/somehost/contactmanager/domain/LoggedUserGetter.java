package pl.somehost.contactmanager.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.security.SecurityUser;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class LoggedUserGetter {

    @Autowired
    IAuthenticationFacade authenticationFacade;
    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedUserGetter.class);

    public User getLoggedUser() {
        return (User) authenticationFacade.getAuthentication().getPrincipal();
    }

    public UserDetails getLoggedUserDetails() {
        return (SecurityUser) authenticationFacade.getAuthentication().getPrincipal();
    }

    public String getLoggedUserName() {
        return authenticationFacade.getAuthentication().getName();
    }

    public String getLoggedUserRoles() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authenticationFacade.getAuthentication().getAuthorities();
        LOGGER.info("Logged user userName and id " + getLoggedUser().getUsername() + " " + getLoggedUser().getId());
        return authorities.stream().map(l -> l.getAuthority()).collect(Collectors.joining(roleJoiningCharacter));
    }
}

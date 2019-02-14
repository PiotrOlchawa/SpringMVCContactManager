package pl.somehost.contactmanager.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AuthenticatedUserRoleHeader {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserRoleHeader.class);

    public String getRoles() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("Logged user userName and id " + user.getUsername() + " " + user.getId() );
        String roles = authorities.stream().map(l -> l.getAuthority()).collect(Collectors.joining(","));
        return roles;
    }
}


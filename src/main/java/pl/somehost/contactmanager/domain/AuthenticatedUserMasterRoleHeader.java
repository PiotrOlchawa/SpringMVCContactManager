package pl.somehost.contactmanager.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AuthenticatedUserMasterRoleHeader {

    public String getRoles() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
               String roles =  authorities.stream().map(l->l.getAuthority()).collect(Collectors.joining(","));
            return roles;
        }
    }


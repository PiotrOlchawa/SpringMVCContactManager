package pl.somehost.contactmanager.config.test;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.security.Authorities;
import pl.somehost.contactmanager.domain.security.Roles;
import pl.somehost.contactmanager.security.SecurityUser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");
        Set<Authorities> authorities = new HashSet<>(Arrays.asList(new Authorities(Roles.ROLE_ADMIN), new Authorities(Roles.ROLE_USER)));
        user.setAuthorities(authorities);
        return new SecurityUser(user);
    }
}

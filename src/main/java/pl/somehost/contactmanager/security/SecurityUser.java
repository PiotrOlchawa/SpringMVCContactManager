package pl.somehost.contactmanager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import pl.somehost.contactmanager.domain.User;

public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = -4381938875186527688L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUser.class);

    public SecurityUser() {
    }

    public SecurityUser(User user) {
        super();
        this.setAuthorities(user.getAuthorities());
        this.setId(user.getId());
        this.setPassword(user.getPassword());
        this.setUsername(user.getUsername());
        this.setAdressBook(user.getAdressBook());
        LOGGER.info("passed username : " + user.getUsername() + " password : " + user.getPassword());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

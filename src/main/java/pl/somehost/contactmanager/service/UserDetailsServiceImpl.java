package pl.somehost.contactmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.repository.UserDao;
import pl.somehost.contactmanager.security.SecurityUser;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info(" User Name " + username);
        LOGGER.info(" All Users ");
        userDao.findAll().stream().forEach(l -> LOGGER.info("Username " + l.getUsername()));
        Optional<User> user = Optional.ofNullable(userDao.findByUsername(username));
        if (!user.isPresent()) {
            LOGGER.info("User was NOT FOUND ");
            throw new UsernameNotFoundException("User was NOT FOUND");
        } else {
            LOGGER.info(" User was FOUND " + user.get().toString());
        }
        return new SecurityUser(user.get());
    }
}

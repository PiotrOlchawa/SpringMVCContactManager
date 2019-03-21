package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.security.LoggedUserGetter;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.exception.UserNotFoundException;
import pl.somehost.contactmanager.repository.UserDao;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoggedUserGetter loggedUserGetter;

    public UserService() {
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    public Optional<User> getUser(Integer userId) {
        return Optional.ofNullable(userDao.findById(userId)).orElseThrow(RuntimeException::new);
    }

    @Secured({"ROLE_ADMIN"})
    public User save(User user) {
        return userDao.save(user);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public User getcurrentUser() {
        return loggedUserGetter.getLoggedUser();
    }

    @Secured({"ROLE_USER"})
    public void saveUser(User user) {
        if (!checkForUserRightsToAccessOrModyfing(user)) {
            throw new RuntimeException("Access is denied !");
        }
        userDao.save(user);
    }

    private boolean checkForUserRightsToAccessOrModyfing(User user) {
        Integer authennticatedUserId = loggedUserGetter.getLoggedUser().getId(); //get id logged in username
        return user.getId() == authennticatedUserId;
    }

    @Secured({"ROLE_ADMIN"})
    public void deleteUser(Integer userId) {
        try {
            userDao.deleteById(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException("User with id: " + userId + " was not found");
        }
    }
}

package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Secured({"ROLE_ADMIN"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/user")
    public void createUser(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping(value = "user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}

package pl.somehost.contactmanager.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "*")
public class UsersManagementController {

    @Autowired
    UserService userService;

    @GetMapping(value ="/users")
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/users")
    void createUser(@RequestBody User user){
        userService.createUser(user);
    }
}

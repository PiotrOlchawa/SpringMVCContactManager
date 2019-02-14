package pl.somehost.contactmanager.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.facade.UserManagementFacade;
import pl.somehost.contactmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "*")
public class UsersManagementController {

    @Autowired
    UserService userService;
    @Autowired
    UserManagementFacade userManagementFacade;

    @Secured({"ROLE_ADMIN"})
    @GetMapping(value ="/users")
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/users")
    void createUser(@RequestBody User user){
        userService.save(user);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/facadeUser")
    void createFacadeUser(@RequestBody UserDto userDto){
        userManagementFacade.createUser(userDto);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/facadeUser")
    void deleteFacadeUser(@RequestParam("id") Integer userId) {
        userManagementFacade.deleteUser(userId);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping (value = "/facadeUser")
    void mofifFacadeUser(@RequestBody UserDto userDto){
        userManagementFacade.modifyUser(userDto);
    }
}

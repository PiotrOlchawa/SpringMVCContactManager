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
@Secured({"ROLE_ADMIN"})
public class UsersManagementController {

    @Autowired
    UserService userService;
    @Autowired
    UserManagementFacade userManagementFacade;

    @GetMapping(value ="/users")
    List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/users")
    void createUser(@RequestBody User user){
        userService.save(user);
    }

    @PostMapping(value = "/facadeUser")
    void createFacadeUser(@RequestBody UserDto userDto){
        userManagementFacade.createUser(userDto);
    }

    @DeleteMapping(value = "/facadeUser")
    void deleteFacadeUser(@RequestParam("id") Integer userId) {
        userManagementFacade.deleteUser(userId);
    }

    @PutMapping (value = "/facadeUser")
    void mofifFacadeUser(@RequestBody UserDto userDto){
        userManagementFacade.modifyUser(userDto);
    }
}

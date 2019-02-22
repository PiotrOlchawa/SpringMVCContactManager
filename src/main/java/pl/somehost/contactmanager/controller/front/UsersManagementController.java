package pl.somehost.contactmanager.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.exception.TestException;
import pl.somehost.contactmanager.facade.UserManagementFacade;
import pl.somehost.contactmanager.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/front-admin")
@CrossOrigin(origins = "*")
@Secured({"ROLE_ADMIN"})
public class UsersManagementController {

    @Autowired
    UserService userService;
    @Autowired
    UserManagementFacade userManagementFacade;

    @GetMapping(value = "/user")
    public RedirectView getUsers(){
        return new RedirectView("/user");
    }

    @PostMapping(value = "/user")
    public void createUser(@Valid @RequestBody UserDto userDto) {
        throw new TestException("Test Exception");
        //userManagementFacade.createUser(userDto);
    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam("id") Integer userId) {
        userManagementFacade.deleteUser(userId);
    }

    @PutMapping (value = "/user")
    public void modifyUser(@Valid @RequestBody UserDto userDto){
        userManagementFacade.modifyUser(userDto);
    }
}

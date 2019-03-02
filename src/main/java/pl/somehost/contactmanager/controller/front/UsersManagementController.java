package pl.somehost.contactmanager.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.somehost.contactmanager.domain.dto.UserDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
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
    public ResponseEntity<ContactManagerResponseMessage>  createUser(@Valid @RequestBody UserDto userDto) {
        return userManagementFacade.createUser(userDto);
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<ContactManagerResponseMessage> deleteUser(@RequestParam(required = true, value = "id" ,defaultValue = "0") Integer userId) {
       return userManagementFacade.deleteUser(userId);
    }

    @PutMapping (value = "/user")
    public ResponseEntity<ContactManagerResponseMessage> modifyUser(@Valid @RequestBody UserDto userDto){
        return userManagementFacade.modifyUser(userDto);
    }
}

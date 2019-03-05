package pl.somehost.contactmanager.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import java.util.List;

@RestController
@RequestMapping(value = "/front-user")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ContactManagementController {

    //Todo: Controllers should function in "bussines roles" instead ordinary roles
    // User should only get contacts that belongs to him and it should be based on business roles authority model
    // instead typical ROLE based model it should be in future branch.

    @Autowired
    ContactManagementFacade contactManagementFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementController.class);

    @GetMapping(value = "/contact")
    public ResponseEntity<List<ContactDto>> getContacts(@AuthenticationPrincipal User user) {
        return contactManagementFacade.getContactsForUser(user);
    }

    @GetMapping(value = "/contact/{id}")
    public RedirectView getContact(@PathVariable String id) {
        return new RedirectView("/contact/" + id);
    }

    @PostMapping(value = "/contact")
    public ResponseEntity<ContactManagerResponseMessage> createContact(@RequestBody ContactDto contactDto) {
        LOGGER.info("Post ContactDto " + contactDto.toString());
        return contactManagementFacade.createContact(contactDto);
    }

    @PutMapping(value = "/contact")
    public ResponseEntity<ContactManagerResponseMessage> updateContact(@RequestBody ContactDto contactDto, @AuthenticationPrincipal User user) {
        return contactManagementFacade.updateContactForUser(contactDto, user);
    }

    @DeleteMapping(value = "/contact")
    public ResponseEntity<ContactManagerResponseMessage> deleteContact(@RequestParam("id") Integer id, @AuthenticationPrincipal User user) {
        return contactManagementFacade.deleteContactForCurrentUser(id, user);
    }

}

package pl.somehost.contactmanager.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.responce.ContactManagerResponseMessage;
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

    @PostMapping(value = "/contact")
    public ResponseEntity<ContactManagerResponseMessage> createContact(@RequestBody ContactDto contactDto) {
        LOGGER.info("Post ContactDto " + contactDto.toString());
        return contactManagementFacade.createContact(contactDto);
    }

    @GetMapping(value = "/contact")
    public List<ContactDto> getContacts() {
        return contactManagementFacade.getContactsForCurrentUser();
    }

    @GetMapping(value = "/contact/{id}")
    public RedirectView getContact(@PathVariable String id) {
        return new RedirectView("/contact/" + id);
    }

    @PutMapping(value = "/contact")
    public void updateContact(@RequestBody ContactDto contactDto) {
        contactManagementFacade.updateContactForCurrentUser(contactDto);
    }

    @DeleteMapping(value = "/contact")
    public void deleteContact(@RequestParam("id") Integer id) {
        contactManagementFacade.deleteContactForCurrentUser(id);
    }

}

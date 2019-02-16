package pl.somehost.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class ContactManagementController {

    @Autowired
    ContactManagementFacade contactManagementFacade;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementController.class);

    @PostMapping(value ="/contact")
        public void createContact(@RequestBody ContactDto contactDto){
        LOGGER.info("Post ContactDto " + contactDto.toString());
        contactManagementFacade.createContact(contactDto);
    }

    @GetMapping(value = "/contact")
    public List<ContactDto> getContacts() {
        return contactManagementFacade.getContactsForCurrentUser();
    }
    
    @PutMapping(value = "/contact")
    public void updateContact(@RequestBody ContactDto contactDto){
        contactManagementFacade.updateContactForCurrentUser(contactDto);
    }
    
    @DeleteMapping(value = "/contact")
    public void deleteContact(@RequestParam("id") Integer id){
        contactManagementFacade.deleteContactForCurrentUser(id);
    }
    
}

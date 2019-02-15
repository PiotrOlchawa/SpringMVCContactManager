package pl.somehost.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

@RestController
@RequestMapping(value = "/user")
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
    public ContactDto getContacts(@RequestParam("id") Integer userId) {
        return contactManagementFacade.getContactsForUser(userId);
    }
}

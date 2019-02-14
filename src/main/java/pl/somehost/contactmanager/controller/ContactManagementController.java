package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.facade.ContactManagementFacade;

@RestController
@RequestMapping(value = "/user")
public class ContactManagementController {

    @Autowired
    ContactManagementFacade contactManagementFacade;

    @GetMapping(value ="/contact")
        public void createContact(ContactDto contactDto){
        contactManagementFacade.createContact(contactDto);
    }

}

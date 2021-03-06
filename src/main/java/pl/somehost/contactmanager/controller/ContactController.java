package pl.somehost.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.service.ContactService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Secured({"ROLE_ADMIN"})
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactMapper contactMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @GetMapping(value = "/contact")
    public List<ContactDto> getContacts() {
        return contactMapper.mapContactListToContactDtoList(contactService.getContacts());
    }

    @GetMapping(value = "/contact/{id}")
    public ContactDto getContact(@PathVariable Integer id) {
        return contactMapper.mapContactToContactDto(contactService.getContact(id));
    }

    @PostMapping(value = "/contact")
    public void createContact(@Valid @RequestBody ContactDto contactDto) {
        LOGGER.info("Post ContactDto " + contactDto.toString());
        contactService.saveContact(contactDto);
    }

    @PutMapping(value = "/contact")
    public void updateContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.updateContact(contactDto);
    }

    @DeleteMapping(value = "/contact/{id}")
    public void deleteContact(@RequestParam("id") Integer id) {
        contactService.deleteContactr(id);
    }

}

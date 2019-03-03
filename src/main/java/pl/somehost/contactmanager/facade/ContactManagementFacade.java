package pl.somehost.contactmanager.facade;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseHeader;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.ContactNotFoundException;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.ResourceLocationService;
import pl.somehost.contactmanager.service.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ContactManagementFacade {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private ContactManagerResponseMessage contactManagerResponseMessage;
    @Autowired
    private ResourceLocationService resourceLocationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementFacade.class);

    public ResponseEntity<ContactManagerResponseMessage> createContact(ContactDto contactDto) {

        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        Contact persistedContact = contactService.saveContact(contact);
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation("/contact/" + persistedContact.getId());
        LOGGER.info("Facade Dto: " + contactDto.toString());
        LOGGER.info("Persisted contactDto: " + contact.getContactInfo());
        LOGGER.info("resourceLocation " + resourceLocation.toString());
        contactManagerResponseMessage.setMessage("Contact was created: " + resourceLocation.toString());
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "Link to resource", resourceLocation);
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<List<ContactDto>> getContactsForCurrentUser() {

        User user = userService.getcurrentUser();
        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        List<ContactDto> contactDtoList = new ArrayList<>();
        if (contactList.size() > 0) {
            contactDtoList = contactMapper.mapContactListToContactDtoList(contactList);
        }
        return new ResponseEntity<>(contactDtoList, HttpStatus.OK);
    }

    public ResponseEntity<ContactManagerResponseMessage> updateContactForCurrentUser(ContactDto contactDto) {

        User user = userService.getcurrentUser();
        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l -> l.getId() == contactDto.getId()).findFirst();
        contact.orElseThrow(() -> new ContactNotFoundException(contactDto.getId(), "not found"));
        Contact contactToPersist = contactMapper.mapContactDtoToContact(contactDto);
        contactService.saveContact(contactToPersist);
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation("/contact/" + contactToPersist.getId());
        LOGGER.info("updateContactForCurrentUser : Update contact to be persisted "
                + contactToPersist.getId() + "," + contactToPersist.getFirstName());
        contactManagerResponseMessage.setMessage("Contact was updated: " + resourceLocation.toString());
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("ContactResponceHeader", "Link to resource", resourceLocation);
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<ContactManagerResponseMessage> deleteContactForCurrentUser(Integer id) {

        User user = userService.getcurrentUser();
        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l -> l.getId() == id).findFirst();
        contact.orElseThrow(() -> new ContactNotFoundException(id, "not found"));
        contactService.deleteContact(id);
        contactManagerResponseMessage.setMessage("Contact with id: " + id + " was deleted sucessfuly");
        LOGGER.info("deleteContactForCurrentUser : Contact was deleted " +contact.get().getContactInfo());
        return new ResponseEntity<>(contactManagerResponseMessage, HttpStatus.OK);
    }



}


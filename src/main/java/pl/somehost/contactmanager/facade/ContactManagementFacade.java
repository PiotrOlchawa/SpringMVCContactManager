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
import pl.somehost.contactmanager.domain.response.CMResponseEntityPreparator;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.exception.ContactNotFoundException;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.ResourceLocationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ContactManagementFacade {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private ResourceLocationService resourceLocationService;
    @Autowired
    private CMResponseEntityPreparator cmResponseEntityPreparator;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementFacade.class);

    public ResponseEntity<ContactManagerResponseMessage> createContact(ContactDto contactDto) {

        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        Contact persistedContact = contactService.saveContact(contact);
        LOGGER.info("Facade Dto: " + contactDto.toString());
        LOGGER.info("Persisted contactDto: " + contact.getContactInfo());

        return cmResponseEntityPreparator.getResponseEntity("Contact with id " + persistedContact.getId() +" was created"
                , "/contact/" + persistedContact.getId(),HttpStatus.CREATED);
    }

    public ResponseEntity<List<ContactDto>> getContactsForUser(User user) {

        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        List<ContactDto> contactDtoList = new ArrayList<>();
        if (contactList.size() > 0) {
            contactDtoList = contactMapper.mapContactListToContactDtoList(contactList);
        }
        return new ResponseEntity<>(contactDtoList, HttpStatus.OK);
    }

    public ResponseEntity<ContactManagerResponseMessage> updateContactForUser(ContactDto contactDto,User user) {

        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l -> l.getId() == contactDto.getId()).findFirst();
        contact.orElseThrow(() -> new ContactNotFoundException(contactDto.getId(), "not found"));
        Contact contactToPersist = contactMapper.mapContactDtoToContact(contactDto);
        contactService.saveContact(contactToPersist);
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation("/contact/" + contactToPersist.getId());
        LOGGER.info("updateContactForUser : Update contact to be persisted "
                + contactToPersist.getId() + "," + contactToPersist.getFirstName());

        return cmResponseEntityPreparator.getResponseEntity("Contact with id " + contact.get().getId() +" was updated"
                , "/contact/" + contact.get().getId(),HttpStatus.OK);
    }

    public ResponseEntity<ContactManagerResponseMessage> deleteContactForCurrentUser(Integer id,User user) {

        List<Contact> contactList = contactService.getContactByAdressBookId(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l -> l.getId() == id).findFirst();
        contact.orElseThrow(() -> new ContactNotFoundException(id, "not found"));
        contactService.deleteContact(id);
        return cmResponseEntityPreparator.getResponseEntity("Contact with id " + id +" was deleted",HttpStatus.OK);
    }



}


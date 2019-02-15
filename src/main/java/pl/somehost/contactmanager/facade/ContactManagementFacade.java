package pl.somehost.contactmanager.facade;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.service.ContactService;
import pl.somehost.contactmanager.service.UserService;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactManagementFacade.class);

    public Contact createContact(ContactDto contactDto) {
        LOGGER.info("Facade Dto " +contactDto.toString());
        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        LOGGER.info("Persisted contactDto id, "+contactDto.getId() +","+contactDto.getFirstName() + " Persisted contact id, "+contact.getId() +","+contact.getFirstName());
        return contactService.saveContact(contact);
    }

    public List<ContactDto> getContactsForCurrentUser() {
        User user = userService.getcurrentUser();

        List<Contact> contactList = contactService.getContactForUser(user.getAdressBook().getId());
        List<ContactDto> contactDtoList = new ArrayList<>();
        if(contactList.size()>0){
            contactDtoList =  contactMapper.mapContactListToContactDtoList(contactList);
        }
        return contactDtoList;
    }

    public void updateContactForCurrentUser(ContactDto contactDto) {
        User user = userService.getcurrentUser();
        List<Contact> contactList = contactService.getContactForUser(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l->l.getId()==contactDto.getId()).findFirst();
        if(contact.isPresent()){
            Contact contactToPersist = contactMapper.mapContactDtoToContact(contactDto);
            LOGGER.info("updateContactForCurrentUser : Update contact to be persisted " +contactToPersist.getId() + "," + contactToPersist.getFirstName());
            contactService.saveContact(contactToPersist);
            return;
        }
        LOGGER.info("updateContactForCurrentUser : Contact is not present " +contactDto.getId());
    }

    public void deleteContactForCurrentUser(Integer id) {
        User user = userService.getcurrentUser();
        List<Contact> contactList = contactService.getContactForUser(user.getAdressBook().getId());
        Optional<Contact> contact = contactList.stream().filter(l->l.getId()==id).findFirst();
        if(contact.isPresent()){
            LOGGER.info("deleteContactForCurrentUser : Delete contact to be persisted " +contact.get().getId() + "," + contact.get().getFirstName());
            contactService.deleteContact(id);
            return;
        }
        LOGGER.info("deleteContactForCurrentUser : Contact is not present ");
    }
}

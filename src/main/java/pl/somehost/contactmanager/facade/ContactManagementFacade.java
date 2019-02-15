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
        Contact contact = contactMapper.mapContactDtoToContactWhileCreateNew(contactDto);
        LOGGER.info("Persisted contactDto id, "+contactDto.getId() +","+contactDto.getFirstName() + " Persisted contact id, "+contact.getId() +","+contact.getFirstName());
        return contactService.saveContact(contact);
    }

    public ContactDto getContactsForUser(Integer userId) {
        User user = userService.getcurrentUser();
        Optional<Contact> contactOptional = contactService.getContactForUser(user.getAdressBook().getId());
        if(contactOptional.isPresent()){
            return contactMapper.mapContactToContactDto(contactOptional.get());
        }
        return new ContactDto();
    }
}

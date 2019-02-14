package pl.somehost.contactmanager.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.mapper.FacadeContactDtoToContactMapper;
import pl.somehost.contactmanager.service.ContactService;


@Component
public class ContactManagementFacade {

    @Autowired
    ContactService contactService;
    @Autowired
    FacadeContactDtoToContactMapper facadeContactDtoToContactMapper;

    public void createContact(ContactDto contactDto) {
        Contact contact = facadeContactDtoToContactMapper.mapFacadeContactDtoToContact(contactDto);
        //Contact persistedContact = contactService.
    }

}

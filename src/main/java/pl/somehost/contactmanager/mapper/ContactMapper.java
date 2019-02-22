package pl.somehost.contactmanager.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    @Autowired
    LoggedUserGetter loggedUserGetter;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactMapper.class);

    public Contact mapContactDtoToContact(ContactDto contactDto) {

        User currentUser = loggedUserGetter.getLoggedUser();

        LOGGER.info("Current User AdressBook id " + currentUser.getAdressBook().getId());
        return new Contact.Builder()
                .setFirstName(contactDto.getFirstName())
                .setLastName(contactDto.getLastName())
                .setId(contactDto.getId())
                .setAptNumber(contactDto.getAptNumber())
                .setEmail(contactDto.getEmail())
                .setStreetAdress(contactDto.getStreetAdress())
                .setTelephone(contactDto.getTelephone())
                .setZipCode(contactDto.getZipCode())
                .setAdressBook(currentUser.getAdressBook())
                .build();
    }

    public List<ContactDto> mapContactListToContactDtoList(List<Contact> contactList) {

        return contactList.stream().map(l -> new ContactDto(l.getId(), l.getFirstName(), l.getLastName(), l.getStreetAdress(),
                l.getZipCode(), l.getAptNumber(), l.getTelephone(), l.getEmail())).collect(Collectors.toList());
    }

}

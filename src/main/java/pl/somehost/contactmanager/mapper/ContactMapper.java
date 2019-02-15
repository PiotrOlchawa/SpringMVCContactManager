package pl.somehost.contactmanager.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.User;
import pl.somehost.contactmanager.domain.dto.ContactDto;

@Component
public class ContactMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactMapper.class);

    public Contact mapContactDtoToContactWhileCreateNew(ContactDto contactDto) {

        User currentUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LOGGER.info("BEFOREEEEEE");

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

    public ContactDto mapContactToContactDto(Contact contact) {

        return null;
    }
}

package pl.somehost.contactmanager.mapper;

import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;

@Component
public class FacadeContactDtoToContactMapper {

    public Contact mapFacadeContactDtoToContact(ContactDto contactDto) {
        return new Contact.Builder()
                .setFirstName(contactDto.getFirstName())
                .setLastName(contactDto.getLastName())
                .setId(contactDto.getId())
                .setAptNumber(contactDto.getAptNumber())
                .setEmail(contactDto.getEmail())
                .setStreetAdress(contactDto.getStreetAdress())
                .setTelephone(contactDto.getTelephone())
                .setZipCode(contactDto.getZipCode())
                .build();
    }
}

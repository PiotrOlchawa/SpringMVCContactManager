package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.exception.ContactNotFoundException;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.repository.ContactDao;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService  {

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private ContactMapper contactMapper;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public Contact saveContact(ContactDto contactDto) {
        return contactDao.save(contactMapper.mapContactDtoToContact(contactDto));
    }

    public List<Contact> getContactByAdressBookId(int id) {
        return contactDao.findByAdressBook_Id(id);
    }

    public void deleteContact(Integer id) {
        contactDao.deleteById(id);
    }

    public Contact getContact(Integer id) {
        Optional<Contact> optionalContact = Optional.ofNullable(contactDao.findById(id))
                .orElseThrow(() -> new ContactNotFoundException(id, "was not found"));
        return optionalContact.get();
    }

    public Contact updateContact(ContactDto contactDto) {
        return contactDao.save(contactMapper.mapContactDtoToContact(contactDto));
    }

    public void deleteContactr(Integer id) {
        contactDao.deleteById(id);
    }

    public List<Contact> getContacts() {
        return contactDao.findAll();
    }
}

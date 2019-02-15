package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.repository.ContactDao;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactDao contactDao;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public Optional<Contact> getContactForUser(int id) {
        return contactDao.findByAdressBook_Id(id);
    }
}

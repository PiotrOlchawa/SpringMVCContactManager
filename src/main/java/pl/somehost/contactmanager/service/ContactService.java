package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.repository.ContactDao;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactDao contactDao;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public List<Contact> getContactForUser(int id) {
        return contactDao.findByAdressBook_Id(id);
    }

    public void deleteContact(Integer id) {
        contactDao.deleteById(id);
    }
}

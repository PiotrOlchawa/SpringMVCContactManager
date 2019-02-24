package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.dto.ContactDto;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.repository.ContactDao;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactDao contactDao;
    @Autowired
    ContactMapper contactMapper;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public Contact saveContact(ContactDto contactDto) {
        return contactDao.save(contactMapper.mapContactDtoToContact(contactDto));
    }

    public List<Contact> getContactUsingAdressBookId(int id) {
        return contactDao.findByAdressBook_Id(id);
    }

    public Contact getContact(Integer id){
        return contactDao.findById(id).get();
    }

    public void deleteContact(Integer id) {
        contactDao.deleteById(id);
    }

    public void createContact(ContactDto contactDto) {
    }

    public List<ContactDto> getContacts(Integer id) {
    return null;
    }

    public void updateContact(ContactDto contactDto) {
    }

    public void deleteContactr(Integer id) {
    }
}

package pl.somehost.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.somehost.contactmanager.repository.ContactDao;

@Service
public class ContactService {

    @Autowired
    ContactDao contactDao;




}

package pl.somehost.contactmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.somehost.contactmanager.domain.Contact;

import java.util.List;

@Repository
public interface ContactDao extends CrudRepository<Contact,Integer> {

    List<Contact> findByAdressBook_Id(Integer id);



}

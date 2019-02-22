package pl.somehost.contactmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.somehost.contactmanager.domain.AdressBook;

@Repository
public interface AdressBookDao extends CrudRepository<AdressBook,Integer> {


}

package pl.somehost.contactmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.somehost.contactmanager.domain.Contact;

import java.util.List;

@Repository("ContactDaoRepository")
@Transactional
public interface ContactDao extends CrudRepository<Contact,Integer> {

    List<Contact> findByAdressBook_Id(Integer id);

    @Override
    @Query("select distinct c from contact c left join fetch c.messageList")
    List<Contact> findAll();

}

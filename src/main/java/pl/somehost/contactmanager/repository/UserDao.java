package pl.somehost.contactmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.somehost.contactmanager.domain.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository("UserDaoRepository")
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    @Override
    @Query("select distinct u from User u left join fetch u.authorities")
    List<User> findAll();

    @Override
    User save(User user);

    Optional<User> findById(Integer id);

    // Jesli nie ma query to wywala lazy inicialization bo w user fetch jest LAZY przy autthorities
    @Query("select distinct u from User u"
            + " left join fetch u.authorities"
            + " where u.username = :username")
    User findByUsername(@Param("username") String username);


}

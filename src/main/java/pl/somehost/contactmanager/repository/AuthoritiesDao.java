package pl.somehost.contactmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.somehost.contactmanager.domain.Authorities;

@Repository
public interface AuthoritiesDao  extends CrudRepository<Authorities,Integer> {

}

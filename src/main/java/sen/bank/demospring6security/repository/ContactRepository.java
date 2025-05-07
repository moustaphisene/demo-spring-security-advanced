package sen.bank.demospring6security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {


}
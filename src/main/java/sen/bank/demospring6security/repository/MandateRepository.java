package sen.bank.demospring6security.repository;

import org.springframework.data.repository.CrudRepository;
import sen.bank.demospring6security.entity.Mandate;

import java.util.Optional;

public interface MandateRepository extends CrudRepository<Mandate, Long> {
    Optional<Mandate> findByEmail(String email) ;
       //

}


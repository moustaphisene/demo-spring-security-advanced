package sen.bank.demospring6security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.Cards;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {

    List<Cards> findByCustomerId(long customerId);

}
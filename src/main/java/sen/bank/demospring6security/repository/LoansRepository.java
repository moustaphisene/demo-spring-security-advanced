package sen.bank.demospring6security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.Loans;

import java.util.List;

@Repository
public interface LoansRepository extends CrudRepository<Loans, Long> {

    List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);

}

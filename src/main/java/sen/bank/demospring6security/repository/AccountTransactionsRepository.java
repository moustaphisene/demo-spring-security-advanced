package sen.bank.demospring6security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.AccountTransactions;

import java.util.List;

@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, String> {

    List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(long customerId);

}
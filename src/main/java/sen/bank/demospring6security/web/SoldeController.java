package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.AccountTransactions;
import sen.bank.demospring6security.repository.AccountTransactionsRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SoldeController {

    private final AccountTransactionsRepository accountTransactionsRepository;

    @GetMapping("/yourSold")
    public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(id);
        if (accountTransactions != null) {
            return accountTransactions;
        } else {
            return null;
        }
    }

}

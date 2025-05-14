package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
//    @PreAuthorize("hasAnyAuthority('VIEWACCOUNT', 'VIEWBALANCE')")
    public ResponseEntity<List<AccountTransactions>> getBalanceDetails(@RequestParam long customerId) {
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(customerId);
        if (accountTransactions == null || accountTransactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(accountTransactions);
        }
    }
    }


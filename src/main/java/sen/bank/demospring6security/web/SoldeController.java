package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.AccountTransactions;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.AccountTransactionsRepository;
import sen.bank.demospring6security.repository.MandateRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SoldeController {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final MandateRepository mandateRepository;

    @GetMapping("/yourSold")
    public ResponseEntity<List<AccountTransactions>> getBalanceDetails(@RequestParam String email) {
        Optional<Mandate> optionalMandate = mandateRepository.findByEmail(email);
        if (optionalMandate.isPresent()) {
            List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                    findByCustomerIdOrderByTransactionDtDesc(optionalMandate.get().getId());
            if (accountTransactions == null || accountTransactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(accountTransactions);
            }

        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    }


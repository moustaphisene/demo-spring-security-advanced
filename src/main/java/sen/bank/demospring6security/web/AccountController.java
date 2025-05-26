package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Accounts;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.AccountsRepository;
import sen.bank.demospring6security.repository.MandateRepository;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;
    private final MandateRepository mandateRepository;


    @GetMapping("/yourAccount")
    public ResponseEntity<Accounts> getAccountDetails(@RequestParam String email) {
        Optional<Mandate> optionalMandate= mandateRepository.findByEmail(email);
        if (optionalMandate.isPresent()) {
            Accounts accounts = accountsRepository.findByCustomerId(optionalMandate.get().getId());
            if (accounts != null) {
                return ResponseEntity.ok(accounts);
            } else {
                return ResponseEntity.notFound().build();
            }

        }else {
            return ResponseEntity.notFound().build();
        }

    }

}

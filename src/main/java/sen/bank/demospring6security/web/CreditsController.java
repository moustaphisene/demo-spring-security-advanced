package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Loans;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.LoansRepository;
import sen.bank.demospring6security.repository.MandateRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CreditsController {

    private final LoansRepository loansRepository;
    private final MandateRepository mandateRepository;

    @GetMapping("/yourCredits")
    @PostAuthorize("hasRole('ROOT')")
    public ResponseEntity<List<Loans>> getLoanDetails(@RequestParam String email) {
        Optional<Mandate> optionalMandate = mandateRepository.findByEmail(email);
        if (optionalMandate.isPresent()) {
            List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(optionalMandate.get().getId());
            if (loans == null || loans.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(loans);
            }
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    }



package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Loans;
import sen.bank.demospring6security.repository.LoansRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreditsController {

    private final LoansRepository loansRepository;

    @GetMapping("/yourCredits")
//    @PreAuthorize("hasAnyAuthority('VIEWLOANS', 'VIEWACCOUNT')")
    public ResponseEntity<List<Loans>> getLoanDetails(@RequestParam long customerId) {
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customerId);
        if (loans == null || loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(loans);
        }
    }
    }



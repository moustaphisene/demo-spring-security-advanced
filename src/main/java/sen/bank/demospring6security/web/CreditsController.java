package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
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
    public List<Loans> getLoanDetails(@RequestParam long id) {
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }

}

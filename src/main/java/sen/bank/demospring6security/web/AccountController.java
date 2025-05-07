package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Accounts;
import sen.bank.demospring6security.repository.AccountsRepository;


@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;


    @GetMapping("/yourAccount")
    public String getAccountDetails(@RequestParam long id) {
        Accounts accounts = accountsRepository.findByCustomerId(id);
        if (accounts != null) {
            return accounts.toString();
        } else {
            return null;
        }
    }
    }

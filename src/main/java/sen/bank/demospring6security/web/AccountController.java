package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/yourAccount")
    public String getAccountDetails(){
        return "your account details from database!";


    }

}

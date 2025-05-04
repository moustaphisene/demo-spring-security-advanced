package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditsController {

    @GetMapping("/yourCredits")
    public String getCreditsDetails(){
        return "your credits details from database!";


    }

}

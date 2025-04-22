package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/yourCard")
    public String getCardDetails(){
        return "your card details from database!";


    }

}

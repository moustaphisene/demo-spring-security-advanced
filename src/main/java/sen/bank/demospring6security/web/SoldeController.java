package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoldeController {

    @GetMapping("/yourSold")
    public String getSoldDetails(){
        return "Your sold details from database!";


    }

}

package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    @GetMapping("/contact")
    public String saveMessageDetails(){
        return "your message details from database!";


    }

}

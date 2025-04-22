package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {

    @GetMapping("/notifications")
    public String getNotifications(){
        return "your notifications from database!";


    }

}

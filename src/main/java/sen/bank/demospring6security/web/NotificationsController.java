package sen.bank.demospring6security.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {

    @GetMapping("/notifications")
//    @Cacheable(value = "activeNotifications") //Clé de la cache
//    public ResponseEntity<List<Notification>> getNotifications() {
//        List<Notification> notifications = notificationRepository.findAllActiveNotifications();
//        return ResponseEntity
//                .ok()
//                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
//                .body(notifications);
//    }
    public String getNotifications(){
        return "your notifications from database!";

    }

}

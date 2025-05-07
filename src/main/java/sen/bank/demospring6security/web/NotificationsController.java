package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Notice;
import sen.bank.demospring6security.repository.NoticeRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NotificationsController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notifications")
//    @Cacheable(value = "activeNotifications") //Clé de la cache
//    public ResponseEntity<List<Notification>> getNotifications() {
//        List<Notification> notifications = notificationRepository.findAllActiveNotifications();
//        return ResponseEntity
//                .ok()
//                .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES))
//                .body(notifications);
//    }
    public ResponseEntity<List<Notice>> getNotices() {
        List<Notice> notices = noticeRepository.findAllActiveNotice();
        if (notices != null) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(notices);
        } else {
            return null;
        }
    }

}

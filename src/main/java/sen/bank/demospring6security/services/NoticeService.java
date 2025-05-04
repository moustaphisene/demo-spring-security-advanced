package sen.bank.demospring6security.services;

import org.springframework.stereotype.Service;
import sen.bank.demospring6security.repository.NotificationsRepository;

@Service
public class NoticeService {
    private final NotificationsRepository notificationsRepository;

    public NoticeService(NotificationsRepository notificationsRepository) {
        NotificationsRepository notificationsRepository1;
        notificationsRepository1 = notificationsRepository;
        notificationsRepository1 = notificationsRepository;
        this.notificationsRepository = notificationsRepository1;
    }

//    @Cacheable(value = "activeNotices")
//    public List<Notifications> getAllActiveNotices() {
//        return noticeRepository.findAllActiveNotices();
//    }
//
//    @CacheEvict(value = "activeNotices", allEntries = true)
//    public Notifications createOrUpdateNotice(Notice notice) {
//        return noticeRepository.save(notice);
//    }
//
//    @CacheEvict(value = "activeNotices", allEntries = true)
//    public void deleteNotice(Long noticeId) {
//        noticeRepository.deleteById(noticeId);
//    }
}

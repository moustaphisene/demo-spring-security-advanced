package sen.bank.demospring6security.services;

import org.springframework.stereotype.Service;
import sen.bank.demospring6security.repository.NoticeRepository;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        NoticeRepository noticeRepository1;
        noticeRepository1 = noticeRepository;
        noticeRepository1 = noticeRepository;
        this.noticeRepository = noticeRepository1;
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

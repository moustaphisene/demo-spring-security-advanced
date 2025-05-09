package sen.bank.demospring6security.services;

import org.springframework.stereotype.Service;
import sen.bank.demospring6security.entity.Notice;
import sen.bank.demospring6security.repository.NoticeRepository;

import java.util.Date;
import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // Renvoie la liste des notices actives
    // @Cacheable(value = "activeNotices") // à activer si tu utilises un cache (ex: Redis, caffeine, etc.)
    public List<Notice> getAllActiveNotices() {
        return noticeRepository.findAllActiveNotice(new Date());
    }


    // Crée ou met à jour une notice
    // @CacheEvict(value = "activeNotices", allEntries = true)
    public Notice createOrUpdateNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    // Supprime une notice
    // @CacheEvict(value = "activeNotices", allEntries = true)
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }



}

package sen.bank.demospring6security.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.Notice;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    @Query(value = "from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();

}
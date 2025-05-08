package sen.bank.demospring6security.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sen.bank.demospring6security.entity.Notice;

import java.util.Date;
import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    @Query( "SELECT n FROM Notice n WHERE :currentDate BETWEEN n.noticBegDt AND n.noticEndDt")
    List<Notice>   findAllActiveNotice(@Param("currentDate") Date currentDate);

}
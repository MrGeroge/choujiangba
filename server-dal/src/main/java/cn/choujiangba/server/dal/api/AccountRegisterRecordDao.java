package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.AccountProfile;
import cn.choujiangba.server.dal.po.AccountRegisterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface AccountRegisterRecordDao extends JpaRepository<AccountRegisterRecord, Long> {
    @Query("from AccountRegisterRecord a where a.create_at between ?1 and ?2")
    List<AccountRegisterRecord> findByCreate_atBetween(Date start,Date finish);
}

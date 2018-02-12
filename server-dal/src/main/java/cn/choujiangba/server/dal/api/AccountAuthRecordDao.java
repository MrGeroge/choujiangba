package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountAuthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface AccountAuthRecordDao extends JpaRepository<AccountAuthRecord, Long>{
    AccountAuthRecord findByToken(String token);
    @Query("from AccountAuthRecord a where a.uid =?1 and a.expire_in>?2")
    List<AccountAuthRecord> findByExpire_inGreaterThan(long uid,Date expire_in);//这里需要的是expire_in大于当前时间的数据
}

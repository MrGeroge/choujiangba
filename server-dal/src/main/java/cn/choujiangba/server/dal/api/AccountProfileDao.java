package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface AccountProfileDao extends JpaRepository<AccountProfile, Long> {
    AccountProfile findByUid(long uId);

    @Modifying
    @Transactional
    @Query("update AccountProfile a set a.balance =?2 where a.id =?1")
    void updateBalance(long id,double balance);

    @Modifying
    @Transactional
    @Query("update AccountProfile a set a.balance =a.balance+?2 where a.id =?1")
    void testUpdateBalance(long id,double balance);

    @Modifying
    @Transactional
    @Query("update AccountProfile a set a.balance =a.balance+?2 where a.uid= ?1")
    void addUserBalance(long uid,double balance);
}

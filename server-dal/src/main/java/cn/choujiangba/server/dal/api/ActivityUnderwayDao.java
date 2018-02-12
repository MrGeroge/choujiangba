package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.ActivityUnderway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by xinghai on 2015/10/25.
 */
@Repository
public interface ActivityUnderwayDao extends JpaRepository<ActivityUnderway, Long> {
    ActivityUnderway findByActivityId(long activityId);
    //
    @Modifying
    @Transactional
    @Query("update ActivityUnderway a set a.realPrice=a.realPrice+?2 where a.id=?1")
    void updateRealPrice(long id,double price);

    @Modifying
    @Transactional
    @Query("delete ActivityUnderway a  where a.activityId=?1")
    void deleteByActivityId(long activityId);
}

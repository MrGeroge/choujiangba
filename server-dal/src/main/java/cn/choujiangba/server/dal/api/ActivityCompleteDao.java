package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by xinghai on 2015/10/25.
 */
@Repository
public interface ActivityCompleteDao extends JpaRepository<ActivityComplete, Long> {
    ActivityComplete findByActivityId(long activityId);
    @Modifying
    @Transactional
    @Query("delete from ActivityComplete a where a.activityId =?1")
    void deleteByActivityId(long activityId);
}

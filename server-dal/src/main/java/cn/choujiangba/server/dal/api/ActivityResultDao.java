package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/10/25.
 */
@Repository
public interface ActivityResultDao extends JpaRepository<ActivityResult, Long> {
    ActivityResult  findByActivityId(long activityId);
}

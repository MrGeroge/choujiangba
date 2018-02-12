package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.ActivityJoinRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xinghai on 2015/10/25.
 */
@Repository
public interface ActivityJoinRecordDao extends JpaRepository<ActivityJoinRecord, Long> {
    ActivityJoinRecord findByUidAndActivityId(long uid,long activityId);
    Page<ActivityJoinRecord> findByActivityId(long activityId,Pageable pageable);
    List<ActivityJoinRecord> findByActivityId(long activityId);
    Page<ActivityJoinRecord> findByUid(long uid,Pageable pageable);
}

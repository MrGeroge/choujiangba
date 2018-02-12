package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/10/25.
 */
@Repository
public interface ActivityDao  extends JpaRepository<Activity, Long> {
    Page<Activity> findByItemId(long itemId,Pageable pageable);
    Activity findByIdAndStatus(long id,int status);
}

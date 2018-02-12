package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.ActivityDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/11/7.
 */
@Repository
public interface ActivityDeliveryDao extends JpaRepository<ActivityDelivery,Long>{
}

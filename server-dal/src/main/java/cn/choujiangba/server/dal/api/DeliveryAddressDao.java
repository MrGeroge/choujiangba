package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.AccountRegisterRecord;
import cn.choujiangba.server.dal.po.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface DeliveryAddressDao extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByUid(long uid);
}

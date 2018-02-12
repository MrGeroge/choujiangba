package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.ItemViewStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/10/21.
 */
@Repository
public interface ItemViewStatisticDao extends JpaRepository<ItemViewStatistic, Long> {
}

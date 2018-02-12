package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.ItemPurchaseStatistic;
import cn.choujiangba.server.dal.po.ItemReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xinghai on 2015/10/21.
 */
@Repository
public interface ItemReplyDao extends JpaRepository<ItemReply, Long> {
    Page<ItemReply> findByItemId(long itemId,Pageable pageable);
    ItemReply findByActivityId(long activityId);
    List<ItemReply> findByItemId(long itemId);
}

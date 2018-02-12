package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Item;
import cn.choujiangba.server.dal.po.ItemBaseStatistic;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xinghai on 2015/10/21.
 */
@Repository
@Transactional
public interface ItemBaseStatisticDao extends JpaRepository<ItemBaseStatistic, Long> {
    @Cacheable(value = "defaultCache",condition = "true")
    ItemBaseStatistic findByItemId(long itemId);
    @Modifying
    @javax.transaction.Transactional
    @Query("update ItemBaseStatistic a set a.published_activity_num =a.published_activity_num+1 where a.itemId =?1")
    void updatePublishedNum(long itemId);

    @Modifying
    @javax.transaction.Transactional
    @Query("update ItemBaseStatistic a set a.reply_num =a.reply_num+1 where a.itemId =?1")
    void updateReplyNum(long itemId);
    @Modifying
    @javax.transaction.Transactional
    @Query("update ItemBaseStatistic a set a.view_num =a.reply_num+1 where a.itemId =?1")
    void updateViewNum(long itemId);
}

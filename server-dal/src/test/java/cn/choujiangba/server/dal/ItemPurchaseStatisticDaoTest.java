package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountProfileDao;
import cn.choujiangba.server.dal.api.ItemPurchaseStatisticDao;
import cn.choujiangba.server.dal.po.ItemPurchaseStatistic;
import cn.choujiangba.server.dal.po.ItemViewStatistic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Created by xinghai on 2015/10/21.
 */
public class ItemPurchaseStatisticDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemPurchaseStatisticDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemPurchaseStatisticDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemPurchaseStatisticDao)context.getBean("itemPurchaseStatisticDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_item_purchase_statistics (item_id,uid,pay,buy_time) VALUES (1,1,0,'2015-9-24 14:22:11')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ItemPurchaseStatistic itemPurchaseStatistic=new ItemPurchaseStatistic();
        itemPurchaseStatistic=dao.findOne((long) 1);
        Assert.assertNotNull(itemPurchaseStatistic);
        Assert.assertEquals(1,itemPurchaseStatistic.getId());
    }

    @Test
    public void testAdd(){
        ItemPurchaseStatistic itemPurchaseStatistic=new ItemPurchaseStatistic();
        itemPurchaseStatistic.setBuy_time(new Date());
        itemPurchaseStatistic.setItem_id(1);
        itemPurchaseStatistic.setPay(1.0);
        itemPurchaseStatistic.setUid(1);
        dao.save(itemPurchaseStatistic);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ItemPurchaseStatistic itemPurchaseStatistic=new ItemPurchaseStatistic();
        itemPurchaseStatistic=dao.findOne((long) 1);
        itemPurchaseStatistic.setUid(2);
        dao.save(itemPurchaseStatistic);
        Assert.assertEquals(1,dao.findAll().size());
    }

}

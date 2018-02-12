package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ItemBaseStatisticDao;
import cn.choujiangba.server.dal.api.ItemDao;
import cn.choujiangba.server.dal.po.Item;
import cn.choujiangba.server.dal.po.ItemBaseStatistic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by xinghai on 2015/10/21.
 */
public class ItemBaseStatisticDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemBaseStatisticDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemBaseStatisticDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemBaseStatisticDao)context.getBean("itemBaseStatisticDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_item_base_statistics (item_id,published_activity_num,view_num,reply_num)  VALUES (1,0,0,0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ItemBaseStatistic itemBaseStatistic=new ItemBaseStatistic();
        itemBaseStatistic=dao.findOne((long) 1);
        Assert.assertNotNull(itemBaseStatistic);
        Assert.assertEquals(1,itemBaseStatistic.getId());
    }

    @Test
    public void testAdd(){
        ItemBaseStatistic itemBaseStatistic=new ItemBaseStatistic();
        itemBaseStatistic.setItemId(2);
        itemBaseStatistic.setPublished_activity_num(0);
        itemBaseStatistic.setReply_num(0);
        itemBaseStatistic.setView_num(0);
        dao.save(itemBaseStatistic);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ItemBaseStatistic itemBaseStatistic=new ItemBaseStatistic();
        itemBaseStatistic=dao.findOne((long) 1);
        itemBaseStatistic.setReply_num(1);
        dao.save(itemBaseStatistic);
        Assert.assertEquals(1,dao.findAll().size());
    }

}

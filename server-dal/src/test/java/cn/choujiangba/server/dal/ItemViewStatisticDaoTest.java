package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ItemBaseStatisticDao;
import cn.choujiangba.server.dal.api.ItemViewStatisticDao;
import cn.choujiangba.server.dal.po.ItemBaseStatistic;
import cn.choujiangba.server.dal.po.ItemViewStatistic;
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
public class ItemViewStatisticDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemViewStatisticDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemViewStatisticDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemViewStatisticDao)context.getBean("itemViewStatisticDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_item_view_statistics (item_id,uid,view_time,ip,city,district,latitiude,longitude)  VALUES (1,1,1,'127.0.0.1','xiantao','wuhan',11.111111,22.222222)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ItemViewStatistic itemViewStatistic=new ItemViewStatistic();
        itemViewStatistic=dao.findOne((long) 1);
        Assert.assertNotNull(itemViewStatistic);
        Assert.assertEquals(1,itemViewStatistic.getId());
    }

    @Test
    public void testAdd(){
        ItemViewStatistic itemViewStatistic=new ItemViewStatistic();
        itemViewStatistic.setUid(1);
        itemViewStatistic.setItem_id(1);
        itemViewStatistic.setCity("hubei");
        itemViewStatistic.setDistrict("zhongguo");
        itemViewStatistic.setView_time(1);
        itemViewStatistic.setIp("127.0.0.1");
        itemViewStatistic.setLatitiude(11.111111);
        itemViewStatistic.setLongitude(22.111111);
        dao.save(itemViewStatistic);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ItemViewStatistic itemViewStatistic=new ItemViewStatistic();
        itemViewStatistic=dao.findOne((long) 1);
        itemViewStatistic.setIp("192.168.0.1");
        dao.save(itemViewStatistic);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

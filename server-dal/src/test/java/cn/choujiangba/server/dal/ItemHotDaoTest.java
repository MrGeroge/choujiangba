package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ActivityCompleteDao;
import cn.choujiangba.server.dal.api.ItemHotDao;
import cn.choujiangba.server.dal.po.ActivityComplete;
import cn.choujiangba.server.dal.po.ItemHot;
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
 * Created by xinghai on 2015/10/25.
 */
public class ItemHotDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemHotDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemHotDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemHotDao)context.getBean("itemHotDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_item_hot (banner_url,item_id)  VALUES ('www.baidu.com',1)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ItemHot itemHot=new ItemHot();
        itemHot=dao.findOne((long) 1);
        Assert.assertNotNull(itemHot);
        Assert.assertEquals(1,itemHot.getId());
    }

    @Test
    public void testAdd(){
        ItemHot itemHot=new ItemHot();
        itemHot.setBannerUrl("www.souhu.com");
        itemHot.setItemId(1);
        dao.save(itemHot);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ItemHot itemHot=new ItemHot();
        itemHot=dao.findOne((long) 1);
        itemHot.setItemId(2);
        dao.save(itemHot);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

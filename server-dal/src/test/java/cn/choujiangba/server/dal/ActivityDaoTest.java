package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.Activity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by xinghai on 2015/10/25.
 */
public class ActivityDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ActivityDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ActivityDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ActivityDao)context.getBean("activityDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_activity (item_id,price,reward_interval,status)  VALUES (1,10.0,1,0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        Activity activity=new Activity();
        activity=dao.findOne((long) 1);
        Assert.assertNotNull(activity);
        Assert.assertEquals(1,activity.getId());
    }

    @Test
    public void testAdd(){
        Activity activity=new Activity();
        activity.setItemId(1);
        activity.setPrice(10.0);
        activity.setRewardInterval(3600);
        activity.setStatus(1);
        dao.save(activity);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        Activity activity=new Activity();
        activity=dao.findOne((long) 1);
        activity.setItemId(2);
        dao.save(activity);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

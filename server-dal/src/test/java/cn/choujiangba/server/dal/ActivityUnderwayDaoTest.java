package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.api.ActivityUnderwayDao;
import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityUnderway;
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
public class ActivityUnderwayDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ActivityUnderwayDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ActivityUnderwayDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ActivityUnderwayDao)context.getBean("activityUnderwayDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_activity_underway (item_id,activity_id,price,real_price)  VALUES (1,1,10.0,10.0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ActivityUnderway activityUnderway=new ActivityUnderway();
        activityUnderway=dao.findOne((long) 1);
        Assert.assertNotNull(activityUnderway);
        Assert.assertEquals(1,activityUnderway.getId());
    }

    @Test
    public void testAdd(){
        ActivityUnderway activityUnderway=new ActivityUnderway();
        activityUnderway.setItemId(1);
        activityUnderway.setActivityId(2);
        activityUnderway.setPrice(10.0);
        activityUnderway.setRealPrice(10.0);
        dao.save(activityUnderway);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ActivityUnderway activityUnderway=new ActivityUnderway();
        activityUnderway=dao.findOne((long) 1);
        activityUnderway.setItemId(2);
        dao.save(activityUnderway);
        Assert.assertEquals(1, dao.findAll().size());
    }
}

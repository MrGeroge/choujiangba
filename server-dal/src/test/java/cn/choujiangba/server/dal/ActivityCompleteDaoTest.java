package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ActivityCompleteDao;
import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityComplete;
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
public class ActivityCompleteDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ActivityCompleteDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ActivityCompleteDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ActivityCompleteDao)context.getBean("activityCompleteDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_activity_complete (item_id,activity_id,price,finish_time)  VALUES (1,1,10.0,'2015-9-24 14:22:11')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ActivityComplete activityComplete=new ActivityComplete();
        activityComplete=dao.findOne((long) 1);
        Assert.assertNotNull(activityComplete);
        Assert.assertEquals(1,activityComplete.getId());
    }

    @Test
    public void testAdd(){
        ActivityComplete activityComplete=new ActivityComplete();
        activityComplete.setItemId(1);
        activityComplete.setPrice(10.0);
        activityComplete.setActivityId(2);
        activityComplete.setFinishTime(new Date());
        dao.save(activityComplete);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ActivityComplete activityComplete=new ActivityComplete();
        activityComplete=dao.findOne((long) 1);
        activityComplete.setItemId(2);
        dao.save(activityComplete);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

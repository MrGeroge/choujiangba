package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.api.ActivityResultDao;
import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityResult;
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
public class ActivityResultDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ActivityResultDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ActivityResultDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ActivityResultDao)context.getBean("activityResultDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_activity_result (item_id,description,activity_id,price,reward_time)  VALUES (1,'hao',1,1,'2015-9-24 14:22:11')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ActivityResult activityResult=new ActivityResult();
        activityResult=dao.findOne((long) 1);
        Assert.assertNotNull(activityResult);
        Assert.assertEquals(1,activityResult.getId());
    }

    @Test
    public void testAdd(){
        ActivityResult activityResult=new ActivityResult();
        activityResult.setItemId(1);
        activityResult.setActivityId(1);
        activityResult.setDesc("hao");
        activityResult.setPrice(1);
        activityResult.setRewardTime(new Date());
        dao.save(activityResult);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ActivityResult activityResult=new ActivityResult();
        activityResult=dao.findOne((long) 1);
        activityResult.setItemId(2);
        dao.save(activityResult);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

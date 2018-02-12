package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.api.ActivityJoinRecordDao;
import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityJoinRecord;
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
public class ActivityJoinRecordDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ActivityJoinRecordDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ActivityJoinRecordDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ActivityJoinRecordDao)context.getBean("activityJoinRecordDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_activity_join_record (item_id,uid,activity_id,ip,join_time,city,district,price)  VALUES (1,1,1,'192.168.0.1','2015-9-24 14:22:11','wuhan','hubei',10.0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ActivityJoinRecord activityJoinRecord=new ActivityJoinRecord();
        activityJoinRecord=dao.findOne((long) 1);
        Assert.assertNotNull(activityJoinRecord);
        Assert.assertEquals(1,activityJoinRecord.getId());
    }

    @Test
    public void testAdd(){
        ActivityJoinRecord activityJoinRecord=new ActivityJoinRecord();
        activityJoinRecord.setItemId(1);
        activityJoinRecord.setPrice(10.0);
        activityJoinRecord.setActivityId(1);
        activityJoinRecord.setCity("changsha");
        activityJoinRecord.setDistrict("hunan");
        activityJoinRecord.setUid(1);
        activityJoinRecord.setJoinTime(new Date());
        activityJoinRecord.setIp("127.0.0.1");
        dao.save(activityJoinRecord);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ActivityJoinRecord activityJoinRecord=new ActivityJoinRecord();
        activityJoinRecord=dao.findOne((long) 1);
        activityJoinRecord.setItemId(2);
        dao.save(activityJoinRecord);
        Assert.assertEquals(1,dao.findAll().size());
    }

}

package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.DeliveryAddressDao;
import cn.choujiangba.server.dal.api.FeedbackDao;
import cn.choujiangba.server.dal.po.DeliveryAddress;
import cn.choujiangba.server.dal.po.Feedback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by xinghai on 2015/10/20.
 */
public class FeedbackDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(FeedbackDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private FeedbackDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(FeedbackDao)context.getBean("feedbackDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_feedback (content,contact,img_urls,status)  VALUES ('hao','18202790149','www.sina.com',0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        Feedback feedback=new Feedback();
        feedback=dao.findOne((long) 1);
        Assert.assertNotNull(feedback);
        Assert.assertEquals(1,feedback.getId());
    }

    @Test
    public void testAdd(){
        Feedback feedback=new Feedback();
        feedback.setContact("13997999537");
        feedback.setContent("hao");
        feedback.setImg_urls("www.baidu.com");
        feedback.setStatus(0);
        dao.save(feedback);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        Feedback feedback=new Feedback();
        feedback=dao.findOne((long) 1);
        feedback.setStatus(1);
        dao.save(feedback);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

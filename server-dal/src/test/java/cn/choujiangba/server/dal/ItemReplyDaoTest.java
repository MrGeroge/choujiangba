package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.ItemReplyDao;
import cn.choujiangba.server.dal.po.ItemReply;
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
public class ItemReplyDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemReplyDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemReplyDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemReplyDao)context.getBean("itemReplyDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO  tb_item_reply (item_id,activity_id,uid,create_at,text_content,img_urls) VALUES (1,1,1,'2015-9-24 14:22:11','hao','www.baidu.com')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        ItemReply itemReply=new ItemReply();
        itemReply=dao.findOne((long) 1);
        Assert.assertNotNull(itemReply);
        Assert.assertEquals(1,itemReply.getId());
    }

    @Test
    public void testAdd(){
        ItemReply itemReply=new ItemReply();
        itemReply.setUid(1);
        itemReply.setItemId(1);
        itemReply.setActivityId(1);
        itemReply.setCreate_at(new Date());
        itemReply.setImg_urls("www.souhu.com");
        itemReply.setText_content("hao");
        dao.save(itemReply);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        ItemReply itemReply=new ItemReply();
        itemReply=dao.findOne((long) 1);
        itemReply.setUid(2);
        dao.save(itemReply);
        Assert.assertEquals(1,dao.findAll().size());
    }

}

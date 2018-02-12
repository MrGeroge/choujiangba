package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.FeedbackDao;
import cn.choujiangba.server.dal.api.ItemDao;
import cn.choujiangba.server.dal.po.Feedback;
import cn.choujiangba.server.dal.po.Item;
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
public class ItemDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(ItemDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private ItemDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(ItemDao)context.getBean("itemDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_item (item_name,property,price,detail,thumbnail_url,desc_img_urls)  VALUES ('shipin','A',10,'haochi','www.baidu.com','www.souhu.com')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        Item item=new Item();
        item=dao.findOne((long) 1);
        Assert.assertNotNull(item);
        Assert.assertEquals(1,item.getId());
    }

    @Test
    public void testAdd(){
        Item item=new Item();
        item.setName("louzi");
        item.setPrice(15);
        item.setDetail("hao");
        item.setDesc_img_urls("www.baidu.com");
        item.setThumbnail_url("www.souhu.com");
        item.setProperty("B");
        dao.save(item);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        Item item=new Item();
        item=dao.findOne((long) 1);
        item.setName("abc");
        dao.save(item);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

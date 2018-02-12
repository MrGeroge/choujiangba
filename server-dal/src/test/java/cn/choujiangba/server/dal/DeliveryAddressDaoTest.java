package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.DeliveryAddressDao;
import cn.choujiangba.server.dal.api.TransactionDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.DeliveryAddress;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by xinghai on 2015/10/19.
 */
public class DeliveryAddressDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(DeliveryAddressDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private DeliveryAddressDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(DeliveryAddressDao)context.getBean("deliveryAddressDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_delivery_address (uid, address_name,phone,zip_code,location)  VALUES (1,'caiqi','18202790149','110','xiantao')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        DeliveryAddress deliveryAddress=new DeliveryAddress();
        deliveryAddress=dao.findOne((long) 1);
        Assert.assertNotNull(deliveryAddress);
        Assert.assertEquals(1,deliveryAddress.getId());
    }

    @Test
    public void testAdd(){
        DeliveryAddress deliveryAddress=new DeliveryAddress();
        deliveryAddress.setUid(1);
        deliveryAddress.setLocation("xiantao");
        deliveryAddress.setName("haha");
        deliveryAddress.setPhone("18202790149");
        deliveryAddress.setZip_code("1223");
        dao.save(deliveryAddress);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        DeliveryAddress deliveryAddress=new DeliveryAddress();
        deliveryAddress=dao.findOne((long) 1);
        deliveryAddress.setName("hahah");
        dao.save(deliveryAddress);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

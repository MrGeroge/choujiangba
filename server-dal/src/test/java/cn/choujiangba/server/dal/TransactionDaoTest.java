package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AdminAccountDao;
import cn.choujiangba.server.dal.api.TransactionDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.Transaction;
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
 * Created by xinghai on 2015/10/19.
 */
public class TransactionDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(TransactionDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private TransactionDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(TransactionDao)context.getBean("transactionDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_transactions (uid, create_at,description,coin_num)  VALUES (1,'2015-9-24 14:22:11','hao',0)";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindOne(){
        Transaction transaction=new Transaction();
        transaction=dao.findOne((long) 1);
        Assert.assertNotNull(transaction);
        Assert.assertEquals(1, transaction.getId());
    }

    @Test
    public void testAdd(){
        Transaction transaction=new Transaction();
        transaction.setCoin_num(1.0);
        transaction.setCreate_at(new Date());
        transaction.setDesc("hao");
        transaction.setUid(1);
        dao.save(transaction);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        Transaction transaction=new Transaction();
        transaction=dao.findOne((long) 1);
        transaction.setCoin_num(1.0);
        dao.save(transaction);
        Assert.assertEquals(1,dao.findAll().size());
    }


    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }
}

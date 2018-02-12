package cn.choujiangba.server.dal;


import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.po.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

public class AccountDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(AccountDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private AccountDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("daos-test-mysql.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(AccountDao)context.getBean("accountDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_account (username, password)  VALUES ('caiqi','123')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        Account account=new Account();
        account=dao.findOne((long) 1);
        Assert.assertNotNull(account);
        Assert.assertEquals(1,account.getId());
    }

    @Test
    public void testFindByUsername(){
        Account account=new Account();
        account=dao.findByUsername("caiqi");
        Assert.assertNotNull(account);
        Assert.assertEquals(1,account.getId());
    }

    @Test
    public void testAdd(){
        Account account=new Account();
        account.setUsername("ckey");
        account.setPassword("123");
        dao.save(account);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        Account account=new Account();
        account=dao.findOne((long) 1);
        account.setPassword("1234");
        dao.save(account);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.api.AccountProfileDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountProfile;
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
public class AccountProfileDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(AccountProfileDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private AccountProfileDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(AccountProfileDao)context.getBean("accountProfileDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql1="INSERT INTO tb_account (username, password)  VALUES ('caiqi','123')";
        final String sql2="INSERT INTO tb_account_profile(uid,balance,nickname,avatar_url,gender) VALUES (1,0.0,'xinghai','www.baidu.com',0)";
        jdbcTemplate.execute(sql1);
        jdbcTemplate.execute(sql2);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        AccountProfile accountProfile=new AccountProfile();
        accountProfile=dao.findOne((long) 1);
        Assert.assertNotNull(accountProfile);
        Assert.assertEquals(1,accountProfile.getId());
    }

    @Test
    public void testAdd(){
        AccountProfile accountProfile=new AccountProfile();
        accountProfile.setUid(2);
        accountProfile.setAvatar_url("www.sina.com");
        accountProfile.setBalance(1.0);
        accountProfile.setGender(0);
        accountProfile.setNickname("ckey");
        dao.save(accountProfile);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        AccountProfile accountProfile=new AccountProfile();
        accountProfile=dao.findOne((long) 1);
        accountProfile.setNickname("hhhh");
        dao.save(accountProfile);
        Assert.assertEquals(1,dao.findAll().size());
    }

    @Test
    public void testUpdateBalance(){
        dao.updateBalance(1,(double) 1);
        AccountProfile accountProfile=new AccountProfile();
        accountProfile=dao.findOne((long) 1);
        Assert.assertNotNull(accountProfile);
        logger.info("balance is"+accountProfile.getBalance());
    }
}

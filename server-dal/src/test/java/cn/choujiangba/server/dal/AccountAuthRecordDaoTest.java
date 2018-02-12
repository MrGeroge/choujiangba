package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountAuthRecordDao;
import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountAuthRecord;
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
public class AccountAuthRecordDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(AccountAuthRecordDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private AccountAuthRecordDao dao;
    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(AccountAuthRecordDao)context.getBean("accountAuthRecordDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql1="INSERT INTO tb_account (username, password)  VALUES ('caiqi','123')";
        final String sql2="INSERT INTO tb_auth_record (uid,token,expire_in,auth_time,device_id,ip,city,district)  VALUES (1,'abc','2015-9-24 14:22:11','2015-9-24 14:22:11','apple','127.0.0.1','wuhan','hubei')";
        jdbcTemplate.execute(sql1);
        jdbcTemplate.execute(sql2);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        AccountAuthRecord accountAuthRecord=new AccountAuthRecord();
        accountAuthRecord=dao.findOne((long) 1);
        Assert.assertNotNull(accountAuthRecord);
        Assert.assertEquals(1,accountAuthRecord.getId());
    }

    @Test
    public void testAdd(){
       AccountAuthRecord accountAuthRecord=new AccountAuthRecord();
        accountAuthRecord.setAuth_time(new Date());
        accountAuthRecord.setExpire_in(new Date());
        accountAuthRecord.setDevice_id("sumsang");
        accountAuthRecord.setToken("edg");
        accountAuthRecord.setIp("127.0.0.1");
        accountAuthRecord.setUid(1);
        accountAuthRecord.setCity("hunan");
        accountAuthRecord.setDistrict("sss");
        dao.save(accountAuthRecord);
        Assert.assertEquals(2, dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        AccountAuthRecord accountAuthRecord=new AccountAuthRecord();
        accountAuthRecord=dao.findOne((long) 1);
        accountAuthRecord.setToken("123");
        dao.save(accountAuthRecord);
        Assert.assertEquals(1,dao.findAll().size());
    }

}

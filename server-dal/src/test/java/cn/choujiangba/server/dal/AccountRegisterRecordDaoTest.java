package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountRegisterRecordDao;
import cn.choujiangba.server.dal.api.DeliveryAddressDao;
import cn.choujiangba.server.dal.po.AccountRegisterRecord;
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
public class AccountRegisterRecordDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(AccountRegisterRecordDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private AccountRegisterRecordDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(AccountRegisterRecordDao)context.getBean("accountRegisterRecordDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_account_register_record (uid, create_at,ip)  VALUES (1,'2015-9-24 14:22:11','127.0.0.1')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        AccountRegisterRecord accountRegisterRecord=new AccountRegisterRecord();
        accountRegisterRecord=dao.findOne((long) 1);
        Assert.assertNotNull(accountRegisterRecord);
        Assert.assertEquals(1, accountRegisterRecord.getId());
    }

    @Test
    public void testAdd(){
        AccountRegisterRecord accountRegisterRecord=new AccountRegisterRecord();
        accountRegisterRecord.setUid(1);
        accountRegisterRecord.setIp("192.168.1.1");
        accountRegisterRecord.setCreate_at(new Date());
        dao.save(accountRegisterRecord);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        AccountRegisterRecord accountRegisterRecord=new AccountRegisterRecord();
        accountRegisterRecord=dao.findOne((long) 1);
        accountRegisterRecord.setIp("127.0.0.1");
        dao.save(accountRegisterRecord);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

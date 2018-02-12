package cn.choujiangba.server.concurrent;

import cn.choujiangba.server.dal.api.AccountProfileDao;
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

public class UpdateBalanceConcurrentTest {
    private static final Logger logger= LoggerFactory.getLogger(UpdateBalanceConcurrentTest.class);
    private JdbcTemplate jdbcTemplate;
    private UpdateBalanceThread updateBalanceThread;
    private AccountProfileDao accountProfileDao;
    /*@Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        updateBalanceThread=(UpdateBalanceThread)context.getBean("updateBalanceThread");
        accountProfileDao=(AccountProfileDao)context.getBean("accountProfileDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(updateBalanceThread);
        Assert.assertNotNull(accountProfileDao);
        final String sql1="INSERT INTO tb_account (username, password)  VALUES ('caiqi','123')";
        final String sql2="INSERT INTO tb_account_profile(uid,balance,nickname,avatar_url,gender) VALUES (1,300,'xinghai','www.baidu.com',0)";
        jdbcTemplate.execute(sql1);
        jdbcTemplate.execute(sql2);
    }
    */

/*
    @Test
    public void testUpdateBalance(){
        Thread thread1=new Thread(updateBalanceThread);
        Thread thread2=new Thread(updateBalanceThread);
        Thread thread3=new Thread(updateBalanceThread);
        Thread thread4=new Thread(updateBalanceThread);
        Thread thread5=new Thread(updateBalanceThread);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("balance is"+accountProfileDao.findOne((long) 1).getBalance());
    }*/
}

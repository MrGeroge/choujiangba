package cn.choujiangba.server.dal;

import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.api.AdminAccountDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AdminAccount;
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
public class AdminAccountDaoTest {
    private static final Logger logger= LoggerFactory.getLogger(AdminAccountDaoTest.class);
    private JdbcTemplate jdbcTemplate;
    private AdminAccountDao dao;

    @Before
    public void prepareData(){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-daos.xml");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
        dao=(AdminAccountDao)context.getBean("adminAccountDao");
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dao);
        final String sql="INSERT INTO tb_admin_account (username, password,email,last_login_time,ip)  VALUES ('caiqi','123','517334815@qq.com','2015-9-24 14:22:11','127.0.0.1')";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testFindAll(){
        Assert.assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindOne(){
        AdminAccount adminAccount=new AdminAccount();
        adminAccount=dao.findOne((long) 1);
        Assert.assertNotNull(adminAccount);
        Assert.assertEquals(1,adminAccount.getId());
    }

    @Test
    public void testAdd(){
        AdminAccount adminAccount=new AdminAccount();
        adminAccount.setUsername("ckey");
        adminAccount.setPassword("123");
        adminAccount.setEmail("18202790149@163.com");
        adminAccount.setLast_login_time(new Date());
        adminAccount.setIp("192.168.1.1");
        dao.save(adminAccount);
        Assert.assertEquals(2,dao.findAll().size());
    }

    @Test
    public void testDelete(){
        dao.delete((long) 1);
        Assert.assertEquals(0,dao.findAll().size());
    }

    @Test
    public void testUpdate(){
        AdminAccount adminAccount=new AdminAccount();
        adminAccount=dao.findOne((long) 1);
        adminAccount.setPassword("1234");
        dao.save(adminAccount);
        Assert.assertEquals(1,dao.findAll().size());
    }
}

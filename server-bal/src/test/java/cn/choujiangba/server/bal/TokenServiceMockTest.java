package cn.choujiangba.server.bal;

import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.TokenDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.service.TokenServiceImpl;
import cn.choujiangba.server.bal.utils.MD5Util;
import cn.choujiangba.server.dal.api.AccountAuthRecordDao;
import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountAuthRecord;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class TokenServiceMockTest {
    private AccountAuthRecordDao accountAuthRecordDao=EasyMock.createMock(AccountAuthRecordDao.class);
    private AccountDao accountDao= EasyMock.createMock(AccountDao.class);
    private TokenService tokenService ;

    private final Logger logger = LoggerFactory.getLogger(Logger.class);
    @Test
    public void testValidate() throws BizException {
        AccountAuthRecord record = new AccountAuthRecord();
        Calendar c= Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        record.setExpire_in(c.getTime());
        record.setToken("123");
        record.setUid(12);
        record.setAuth_time(new Date());
        record.setDevice_id("123");
        record.setCity("wuhan");
        record.setDistrict("wuchang");
        record.setIp("192.168.1.1");
        EasyMock.expect(accountAuthRecordDao.findByToken("123")).andReturn(record);
        EasyMock.replay(accountAuthRecordDao);

        tokenService = new TokenServiceImpl().setAccountAuthRecordDao(accountAuthRecordDao);
        long uId = tokenService.validate("123");
        System.out.println(uId);
        Assert.assertNotNull(uId);
    }

    @Test
    public void testMD5(){
        String password = MD5Util.encoding("123456");

        logger.info(password);

        Assert.assertNotNull(password);
    }
}

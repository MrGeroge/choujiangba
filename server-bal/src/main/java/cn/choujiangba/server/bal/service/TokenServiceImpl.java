package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.TokenDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.utils.MD5Util;
import cn.choujiangba.server.dal.api.AccountAuthRecordDao;
import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountAuthRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class TokenServiceImpl implements TokenService{
    private static Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountAuthRecordDao accountAuthRecordDao;
    private AccountDao accountDao;
    @Autowired
    public TokenServiceImpl setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
        return this;
    }

    @Autowired
    public TokenServiceImpl setAccountAuthRecordDao(AccountAuthRecordDao accountAuthRecordDao) {
        this.accountAuthRecordDao = accountAuthRecordDao;
        return this;
    }

    @Override
    public long validate(String token) throws BizException {
        long uId;

        if(token==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "token is null!");
        AccountAuthRecord authRecord;
        authRecord=accountAuthRecordDao.findByToken(token);
        if(authRecord==null)
            throw new BizException(BizException.ERROR_CODE_TOKEN_NO_MAPPING,"There is no such token mapping data");
        if(authRecord.getExpire_in().before(new Date()))
            throw new BizException(BizException.ERROR_CODE_TOKEN_TIMEOUT,"token timed out");

        uId=authRecord.getUid();
        logger.info(String.format("userId=%d,successfully login",uId));
        return uId;
    }

    @Override
    public TokenDTO authorize(String username, String password, String city, String district, String deviceId, String ip) throws BizException {
        logger.info(String.format("Login username=%s,password=%s,city=%s,district=%s,device=%s,ip=%s",username,password,city,district,deviceId,ip));
        Account account;
        AccountAuthRecord accountAuthRecord;
        Date date ;//用来产生token的时间
        Random random;//用于产生随机数
        String token;
        Calendar calendar;
        TokenDTO tokenDTO;

        //用户名为空或者密码为空时抛出异常
        if(username != null && !"".equals(username)) {
            account = accountDao.findByUsername(username);
            if(password == null || "".equals(password)){
                throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "password is null!");
            }
        }else{
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "username is null!");
        }
        if(deviceId==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"deviceId is null");
        if(ip==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"ip is null");
        if(account!=null){
            if(account.getPassword().equals(MD5Util.encoding(password))){
                List<AccountAuthRecord> records = accountAuthRecordDao.findByExpire_inGreaterThan(account.getId(), new Date());
                if(records!=null){
                    for(AccountAuthRecord item:records){
                        logger.info(String.format("userId=%d,login at another item",item.getUid()));
                        item.setExpire_in(new Date());
                        accountAuthRecordDao.save(item);
                    }
                }
                date = new Date();
                random = new Random();
                token= MD5Util.encoding(date.toString() + random.nextInt(1000));//根据当前时间和一个随机数产生唯一的token
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                accountAuthRecord = new AccountAuthRecord();
                accountAuthRecord.setAuth_time(new Date());
                accountAuthRecord.setDevice_id(deviceId);
                accountAuthRecord.setIp(ip);
                accountAuthRecord.setUid(account.getId());
                accountAuthRecord.setCity(city);
                accountAuthRecord.setDistrict(district);
                accountAuthRecord.setToken(token);
                accountAuthRecord.setExpire_in(calendar.getTime());
                accountAuthRecordDao.save(accountAuthRecord);

                tokenDTO = new TokenDTO();
                tokenDTO.setUid(account.getId());
                tokenDTO.setToken(token);
                tokenDTO.setExpireIn(24*60*60);
                return tokenDTO;
            }
            else {
                throw new BizException(BizException.ERROR_CODE_PASSWORD_ERROR,"password wrong!");
            }
        }else{
            throw new BizException(BizException.ERROR_CODE_USER_NOT_FOUND,"user not exist!");
        }
    }

    @Override
    public void delete(String token) throws BizException {
        logger.info("delete token="+token);
        if(token==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "token is null!");
        AccountAuthRecord authRecord;
        authRecord=accountAuthRecordDao.findByToken(token);
        if(authRecord==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"There is no such token mapping data");
        logger.info(String.format("userId=%d successfully logout",authRecord.getUid()));
        authRecord.setExpire_in(new Date());
        accountAuthRecordDao.save(authRecord);
    }
}

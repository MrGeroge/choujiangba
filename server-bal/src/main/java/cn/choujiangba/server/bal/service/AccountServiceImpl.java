package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.AccountService;
import cn.choujiangba.server.bal.dto.AccountDTO;
import cn.choujiangba.server.bal.dto.AddressDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.dto.TransactionRecordDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.utils.MD5Util;
import cn.choujiangba.server.dal.api.*;
import cn.choujiangba.server.dal.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;


import java.util.*;

/**
 * Created by shuiyu on 2015/10/19
 *
 * Edited by zhangyu on 2015/10/21
 *           - 优化 log 日志信息
 *           - 优化 异常信息
 */
public class AccountServiceImpl implements AccountService{
    private static Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountDao accountDao;
    private AccountProfileDao accountProfileDao;
    private DeliveryAddressDao deliveryAddressDao;
    private AccountRegisterRecordDao accountRegisterRecordDao;
    private TransactionDao transactionDao;

    @Autowired

    public void setTransactionDao(TransactionDao transactionDao) {

        this.transactionDao = transactionDao;

    }

    @Autowired
    public AccountServiceImpl setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
        return this;
    }
    @Autowired
    public AccountServiceImpl setAccountProfileDao(AccountProfileDao accountProfileDao) {
        this.accountProfileDao = accountProfileDao;
        return this;
    }
    @Autowired
    public AccountServiceImpl setDeliveryAddressDao(DeliveryAddressDao deliveryAddressDao) {
        this.deliveryAddressDao = deliveryAddressDao;
        return this;
    }
    @Autowired
    public AccountServiceImpl setAccountRegisterRecordDao(AccountRegisterRecordDao accountRegisterRecordDao) {
        this.accountRegisterRecordDao = accountRegisterRecordDao;
        return this;
    }

    @Override
    public void updatePassword(String username, String newPassword) throws BizException {
        //用户名或新密码为空时抛出异常
        if( StringUtils.isEmpty(username) || StringUtils.isEmpty(newPassword) )
                throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"update password failed,cause:username or password null");

        logger.info(String.format("execute updatePassword username=%s,newPass=%s",username,newPassword));

        Account account = accountDao.findByUsername(username);

        if(account != null){
            //设置账户加密后密码并保存
            account.setPassword(MD5Util.encoding(newPassword));
            accountDao.save(account);

            logger.info(String.format("update password success ,info [uid=%d,username=%s,password=%s]",account.getId(),account.getUsername(),account.getPassword()));
        }else{
            throw new BizException(BizException.ERROR_CODE_USER_NOT_FOUND,String.format("update password failed,cause:%s not found",username));
        }
    }

    @Override
    public long register(String username, String password,String ip) throws BizException {
        if( StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(ip))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "execute register failed,cause:need params");

        logger.info(String.format("execute register,info : username=%s,password=%s,ip=%s",username,password,ip));

        if(accountDao.findByUsername(username)!=null){
            throw new BizException(BizException.ERROR_CODE_USERNAME_EXISTED,String.format("%s existed", username));
        }

        //保存基本账号信息
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(MD5Util.encoding(password));
        account = accountDao.save(account);

        if(account.getId()<1)
            throw new RuntimeException(String.format("save account failed,info [username=%s,password=%s]",username,password));

        //创建详细资料
        AccountProfile profile = new AccountProfile();
        profile.setUid(account.getId());
        profile.setBalance(0);

        accountProfileDao.save(profile);

        //创建注册记录
        AccountRegisterRecord record = new AccountRegisterRecord();
        record.setUid(account.getId());
        record.setCreate_at(new Date());
        record.setIp(ip);

        accountRegisterRecordDao.save(record);

        logger.info(String.format("execute register success,info[uid=%d,username=%s,password=%s]",account.getId(),username,password));

        return account.getId();
    }

    @Override
    public void updateAccountInfo(AccountDTO info) throws BizException {
        if( info ==null )
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "execute updateAccountInfo failed,cause:info is null");

        if( info.getUserId() < 1 )
            throw new RuntimeException(String.format("execute updateAccountInfo failed,userid error"));

        logger.info(String.format("update Account Info nickname=%s,gender=%s avatarUrl=%s",info.getNickname(),
                info.getGender(),
                info.getAvatarUrl()));

        //更新用户详细信息
        AccountProfile profile = new AccountProfile();
        profile.setId(info.getUserId());
        profile.setUid(info.getUserId());
        profile.setNickname(info.getNickname());
        profile.setGender(info.getGender().equals(AccountDTO.Gender.MALE) ? 0 : 1);
        profile.setAvatar_url(info.getAvatarUrl());

        accountProfileDao.save(profile);

        logger.info("update account info success userId = " + profile.getUid());
    }

    @Override
    public AccountDTO getUserInfo(long userId) throws BizException {

        logger.info(String.format("get myInfo id=%d",userId));
        //用户ID小于1时抛出异常
        if(userId < 1){
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "userId format is incorrect!");
        }
        AccountProfile profile = accountProfileDao.findByUid(userId);
        if(profile==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND, "account detail instance not found");
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setNickname(profile.getNickname());
        accountDTO.setAvatarUrl(profile.getAvatar_url());
        accountDTO.setGender(profile.getGender() == 0 ? AccountDTO.Gender.MALE : AccountDTO.Gender.FEMALE);
        logger.info("Successfully get info");
        return accountDTO;
    }

    @Override
    public List<AddressDTO> listMyAddress(long userId) throws BizException {

        logger.info(String.format("get myInfo id=%d",userId));
        if(userId < 1){
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "userId format is incorrect!");
        }
        List<AddressDTO> addresses = new LinkedList<>();
        List<DeliveryAddress> items = deliveryAddressDao.findByUid(userId);
        if(items==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"there is no such mapping data");
        for(DeliveryAddress item:items){
            AddressDTO address = new AddressDTO();
            address.setLocation(item.getLocation());
            address.setName(item.getName());
            address.setPhone(item.getPhone());
            address.setId(item.getId());
            address.setZipCode(item.getZip_code());
            addresses.add(address);
        }
        return addresses;
    }

    @Override
    public void addAddress(long userId, AddressDTO address) throws BizException {
        logger.info(String.format("AddAddress addressDto=%s",address.toString()));
        if(userId < 1){
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "userId format is incorrect!");
        }
        if(address.getLocation()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"location is null");
        if(address.getName()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"name is null");
        if(address.getPhone()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"phone is null");
        if(address.getZipCode()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"zip_code is null");
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setPhone(address.getPhone());
        deliveryAddress.setName(address.getName());
        deliveryAddress.setLocation(address.getLocation());
        deliveryAddress.setUid(userId);
        deliveryAddress.setZip_code(address.getZipCode());
        deliveryAddressDao.save(deliveryAddress);
    }

    @Override
    public void deleteAddress(long addressId) throws BizException {
        if(addressId<1)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "addressId format is incorrect!");

        deliveryAddressDao.delete(addressId);
    }

    @Override
    public void updateAddress(AddressDTO address,long userId) throws BizException {
        logger.info(address.toString());
        if(userId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"user Id is not correct");
        if(address.getId() < 1)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "addressId  is incorrect!");
        DeliveryAddress deliveryAddress = deliveryAddressDao.findOne(address.getId());
        if(deliveryAddress.getUid()!=userId)
            throw new BizException(BizException.ERROR_CODE_AUTH_NOT_CORRECT,"Error auth");
        if(deliveryAddress==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"no such address data");
        if(address.getLocation()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"location is null");
        if(address.getName()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"name is null");
        if(address.getPhone()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"phone is null");
        if(address.getZipCode()==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"zip_code is null");
        deliveryAddress.setZip_code(address.getZipCode());
        deliveryAddress.setLocation(address.getLocation());
        deliveryAddress.setName(address.getName());
        deliveryAddress.setId(address.getId());
        deliveryAddress.setPhone(address.getPhone());
        deliveryAddressDao.save(deliveryAddress);
    }

    @Override
    public double getMyBalance(long userId) throws BizException {
        if(userId < 1)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "userId  is incorrect!");
        AccountProfile profile=accountProfileDao.findByUid(userId);
        return profile.getBalance();
    }

    @Override
    public List<Date> getUserStatistic(Date start, Date finish) throws BizException {
        if(start==null||finish==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"param null pointer");
        List<AccountRegisterRecord> records = accountRegisterRecordDao.findByCreate_atBetween(start,finish);
        if(records==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"There is no such data");
        List<Date>items = new LinkedList<>();
        for(AccountRegisterRecord record:records)
            items.add(record.getCreate_at());
        return items;
    }

    @Override
    public Pageable<TransactionRecordDTO> listMyRecord(int page, int count, long uid) throws BizException {
        if(page<1||count<1||count>50)

            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));

        Sort sort = new Sort(Sort.Direction.DESC, "id");

        Page<Transaction> transactions = transactionDao.findByUid(uid,new PageRequest(page - 1, count, sort));

        Pageable<TransactionRecordDTO> transDTOs = new Pageable<>();

        transDTOs.setCurrentPage(page);
        transDTOs.setHasNextPage(transactions.hasNext());
        transDTOs.setHasPrePage(transactions.hasPrevious());

        for(Transaction item:transactions){
            TransactionRecordDTO transactionRecordDTO = new TransactionRecordDTO();
            transactionRecordDTO.setUid(item.getUid());
            transactionRecordDTO.setCoinNum(item.getCoin_num());
            transactionRecordDTO.setDate(item.getCreate_at());
            transactionRecordDTO.setDes(item.getDesc());
            transactionRecordDTO.setTid(item.getId());
            transDTOs.getContent().add(transactionRecordDTO);
        }
        logger.info("successfully get my transaction "+transDTOs.toString());
        return transDTOs;

    }

    @Override
    public void addUserBalance(long uid,double value, String desc) throws BizException {
        logger.info(String.format("execute addUserBalance uid=%d,value=%f,desc=%s",uid,value,desc));
    if(uid<1){
        throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "userId  is incorrect!");
    }
        if(value<0){
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL, "balance  is incorrect!");
        }
        if(StringUtils.isEmpty(desc)){
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"desc is null");
        }
        Transaction transaction=new Transaction();
        transaction.setUid(uid);
        transaction.setCreate_at(new Date());
        transaction.setCoin_num(value);
        transaction.setDesc(desc);
        transactionDao.save(transaction);
        logger.info(String.format("add transaction successfully transactionId=%d",transaction.getId()));
        accountProfileDao.addUserBalance(uid,value);
        logger.info("add user balance succussfully");
    }

}


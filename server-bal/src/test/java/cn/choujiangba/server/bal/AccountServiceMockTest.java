package cn.choujiangba.server.bal;

import cn.choujiangba.server.bal.api.AccountService;
import cn.choujiangba.server.bal.dto.AccountDTO;
import cn.choujiangba.server.bal.dto.AddressDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.service.AccountServiceImpl;
import cn.choujiangba.server.bal.utils.MD5Util;
import cn.choujiangba.server.dal.api.AccountDao;
import cn.choujiangba.server.dal.api.AccountProfileDao;
import cn.choujiangba.server.dal.api.AccountRegisterRecordDao;
import cn.choujiangba.server.dal.api.DeliveryAddressDao;
import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AccountProfile;
import cn.choujiangba.server.dal.po.AccountRegisterRecord;
import cn.choujiangba.server.dal.po.DeliveryAddress;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class AccountServiceMockTest {
    private AccountDao accountDao = EasyMock.createMock(AccountDao.class);
    private AccountProfileDao accountProfileDao=EasyMock.createMock(AccountProfileDao.class);
    private DeliveryAddressDao deliveryAddressDao=EasyMock.createMock(DeliveryAddressDao.class);
    private AccountRegisterRecordDao accountRegisterRecordDao=EasyMock.createMock(AccountRegisterRecordDao.class);
    private AccountService accountService;
    @Test
    public void testUpdatePassword() throws BizException {
        Account account =new Account();
        account.setUsername("123");
        account.setId(1);
        account.setPassword(MD5Util.encoding("123"));
        EasyMock.expect(accountDao.findByUsername("123")).andReturn(account);
        EasyMock.expect(accountDao.save(account)).andReturn(account);
        EasyMock.replay(accountDao);
        accountService = new AccountServiceImpl().setAccountDao(accountDao);
        accountService.updatePassword("123","123");
    }
    @Test
    public void testGetUserInfo() throws BizException {
        AccountProfile profile = new AccountProfile();
        profile.setBalance(123);
        profile.setNickname("123");
        profile.setAvatar_url("123");
        profile.setId(123);
        profile.setUid(123);
        profile.setGender(0);
        EasyMock.expect(accountProfileDao.findByUid(123)).andReturn(profile);
        EasyMock.replay(accountProfileDao);
        accountService=new AccountServiceImpl().setAccountProfileDao(accountProfileDao);
        AccountDTO accountDTO=accountService.getUserInfo(123);
        Assert.assertNotNull(accountDTO);
        Assert.assertEquals(accountDTO.getNickname(), profile.getNickname());
    }
    @Test
    public void testListMyAddress() throws BizException {
        List<DeliveryAddress> items=new LinkedList<>();
        for(int i=0;i<3;i++){
            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryAddress.setPhone("123");
            deliveryAddress.setName("123");
            deliveryAddress.setUid(123);
            deliveryAddress.setId(i);
            deliveryAddress.setLocation("wuhan");
            deliveryAddress.setZip_code("430063");
            items.add(deliveryAddress);
        }
        EasyMock.expect(deliveryAddressDao.findByUid(123)).andReturn(items);
        EasyMock.replay(deliveryAddressDao);
        accountService = new AccountServiceImpl().setDeliveryAddressDao(deliveryAddressDao);
        List<AddressDTO> addresses = accountService.listMyAddress(123);
        Assert.assertNotNull(addresses);
        Assert.assertEquals(3,addresses.size());
    }
    @Test
    public void testAddAddress() throws BizException {
        AddressDTO address =new AddressDTO();
        address.setName("123");
        address.setZipCode("123");
        address.setLocation("123");
        address.setId(123);
        address.setPhone("123");
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setPhone("123");
        deliveryAddress.setName("123");
        deliveryAddress.setUid(123);
        deliveryAddress.setId(1);
        deliveryAddress.setLocation("123");
        deliveryAddress.setZip_code("123");
        EasyMock.expect(deliveryAddressDao.findOne((long) 123)).andReturn(deliveryAddress);
        EasyMock.expect(deliveryAddressDao.save(deliveryAddress)).andReturn(deliveryAddress);
        EasyMock.replay(deliveryAddressDao);
        accountService = new AccountServiceImpl().setDeliveryAddressDao(deliveryAddressDao);
        accountService.updateAddress(address,123);
    }
    @Test
    public void testGetUserStatistic() throws BizException {
        List<AccountRegisterRecord> items=new LinkedList<>();
        Date date = new Date();
        for(int i=0;i<3;i++){
            AccountRegisterRecord item = new AccountRegisterRecord();
            item.setId(i);
            item.setIp("123");
            item.setCreate_at(new Date());
            item.setUid(123);
            items.add(item);
        }
        EasyMock.expect(accountRegisterRecordDao.findByCreate_atBetween(date,date)).andReturn(items);
        EasyMock.replay(accountRegisterRecordDao);
        accountService = new AccountServiceImpl().setAccountRegisterRecordDao(accountRegisterRecordDao);
        List<Date>data=accountService.getUserStatistic(date, date);
        Assert.assertNotNull(data);
        Assert.assertEquals(3,data.size());
    }
}

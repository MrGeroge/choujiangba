package ch.choujiangba.server.app;

import cn.choujiangba.server.app.controller.AccountController;
import cn.choujiangba.server.app.vo.*;
import cn.choujiangba.server.bal.api.AccountService;
import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.AccountDTO;
import cn.choujiangba.server.bal.dto.AddressDTO;
import cn.choujiangba.server.bal.dto.TokenDTO;
import cn.choujiangba.server.bal.exception.BizException;
import org.apache.commons.codec.binary.Base64;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/20.
 */
public class AccountControllerTest {

    private AccountController accountController;

    IMocksControl control = EasyMock.createControl();
    private AccountService accountService = control.createMock(AccountService.class);
    private TokenService tokenService = control.createMock(TokenService.class);

    @Test
    public void testLogin() throws Exception {
        /**单元测试通不过，因为ip不能统一*/
        long userId = 1;
        double balance = 12;
        String encodeStr;

        encodeStr = Base64.encodeBase64String("12345678".getBytes());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUid(userId);
        tokenDTO.setToken("32JFKJGHFKSDJ3DJ7483D");
        //tokenDTO.setExpireIn(new Date(2015, 10, 23));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(1);
        accountDTO.setNickname("zhangsan");
        accountDTO.setAvatarUrl("www.baidu.com");
        accountDTO.setGender(AccountDTO.Gender.MALE);

        EasyMock.expect(tokenService.authorize("18202740055", "12345678", "", "", "23", "192.168.1.1")).andReturn(tokenDTO);
        EasyMock.expect(tokenService.authorize("18211012138", "12345678", "", "", "23", "192.168.1.1")).andThrow(
                new BizException(BizException.ERROR_CODE_USER_NOT_FOUND, "无此用户")).anyTimes();
        EasyMock.expect(tokenService.authorize("18202740055", "12345678", "", "", "23", "192.168.1.1")).andThrow(
                new BizException(BizException.ERROR_CODE_PASSWORD_ERROR, "密码错误")).anyTimes();
        //EasyMock.expect(tokenService.validate(tokenDTO.getToken())).andReturn(userId);
        EasyMock.expect(accountService.getMyBalance(userId)).andReturn(balance);
        EasyMock.expect(accountService.getUserInfo(userId)).andReturn(accountDTO);

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        LoginResult loginResult = (LoginResult)accountController.login("18202740055", encodeStr, "23", "", "", "", "");
        //Assert.assertEquals(LoginResult.SUCCESS_STATUS, loginResult.getStatus());
        Assert.assertEquals("登陆成功", loginResult.getMessage());
        Assert.assertEquals("32JFKJGHFKSDJ3DJ7483D", loginResult.getAuth().getToken());
        Assert.assertEquals("male", loginResult.getUser_detail().getGender());

    }

    @Test
    public void testLogout()throws Exception{
        String token = "32JFKJGHFKSDJ3DJ7483D";

        tokenService.delete(token);
        EasyMock.expectLastCall();

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        CommonResult commonResult = accountController.logout(token);

        Assert.assertNotNull(commonResult);
        Assert.assertEquals(CommonResult.SUCCESS_STATUS,commonResult.getResult());
    }

    @Test
    public void testRegister()throws Exception{
        long userId = 1;
        String encodeStr = Base64.encodeBase64String("12345678".getBytes());

        //EasyMock.expect(accountService.register("18202740055", "12345678", "nickname", "192.168.1.1")).andReturn(userId);
        EasyMock.expect(accountService.register("18211012138","12345678","192.168.1.1")).andThrow(
              new BizException(BizException.ERROR_CODE_USERNAME_EXISTED, "用户已存在，请直接登录")).anyTimes();

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        //CommonResult commonResult1 = accountController.register("18202740055",encodeStr,"nickname");

        //Assert.assertEquals(CommonResult.SUCCESS_STATUS, commonResult1.getResult());
        //Assert.assertEquals("注册成功",commonResult1.getMessage());

        CommonResult commonResult2 = accountController.register("18211012138", encodeStr, "nickname");
        Assert.assertEquals(CommonResult.FAILED_STATUS, commonResult2.getResult());
        Assert.assertEquals("用户已存在，请直接登录",commonResult2.getMessage());
    }

    @Test
    public void testGetProfile()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(1);
        accountDTO.setNickname("zhangsan");
        accountDTO.setAvatarUrl("www.baidu.com");
        accountDTO.setGender(AccountDTO.Gender.MALE);

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        EasyMock.expect(accountService.getUserInfo(userId)).andReturn(accountDTO);
        //EasyMock.expect(accountService.getUserInfo(userId)).andThrow(
          //      new BizException(BizException.ERROR_CODE_TOKEN_TIMEOUT, "空"));

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        UserDetailVO userDetailVO = (UserDetailVO)accountController.getProfile(token);

        Assert.assertEquals("zhangsan",userDetailVO.getNickname());
        Assert.assertEquals("male",userDetailVO.getGender());
        Assert.assertEquals(1,userDetailVO.getUid());

    }

    @Test
    public void testUpdateProfile()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(1);
        accountDTO.setNickname("zhangsan");
        accountDTO.setAvatarUrl("www.baidu.com");
        accountDTO.setGender(AccountDTO.Gender.MALE);

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        accountService.updateAccountInfo(accountDTO);
        EasyMock.expectLastCall();

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        CommonResult commonResult = accountController.updateProfile(token,"zhangsan","male","www.baidu.com");

        Assert.assertEquals(CommonResult.SUCCESS_STATUS,commonResult.getResult());
        Assert.assertEquals("更新资料成功",commonResult.getMessage());
    }

    @Test
    public void testGetBalance()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        EasyMock.expect(accountService.getMyBalance(userId)).andReturn(12d);

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        BalanceVO balanceVO = accountController.getBalance(token);
        Assert.assertEquals(12, balanceVO.getBalance(), 0);
    }

    @Test
    public void testListAddress()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        AddressDTO addressDTO1 = new AddressDTO();
        AddressDTO addressDTO2 = new AddressDTO();
        AddressDTO addressDTO3 = new AddressDTO();
        AddressDTO addressDTO4 = new AddressDTO();
        addressDTO1.setId(1);
        addressDTO2.setId(2);
        addressDTO3.setId(3);
        addressDTO4.setId(4);
        addressDTO1.setName("address1");
        addressDTO2.setName("address2");
        addressDTO3.setName("address3");
        addressDTO4.setName("address4");
        addressDTO1.setPhone("18202740055");
        addressDTO2.setPhone("18202740055");
        addressDTO3.setPhone("18202740055");
        addressDTO4.setPhone("18202740055");
        addressDTO1.setLocation("location1");
        addressDTO2.setLocation("location2");
        addressDTO3.setLocation("location3");
        addressDTO4.setLocation("location4");
        addressDTO1.setZipCode("123456");
        addressDTO2.setZipCode("123456");
        addressDTO3.setZipCode("123456");
        addressDTO4.setZipCode("123456");

        List<AddressDTO> list = new LinkedList<>();
        list.add(addressDTO1);
        list.add(addressDTO2);
        list.add(addressDTO3);
        list.add(addressDTO4);

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        EasyMock.expect(accountService.listMyAddress(userId)).andReturn(list);

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        List<AddressVO> l = accountController.listAddress(token);
        Assert.assertEquals(4,l.size());
        Assert.assertEquals("address1",l.get(0).getName());
    }

    @Test
    public void testAddAddress()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setName("address1");
        addressDTO.setPhone("18202740055");
        addressDTO.setLocation("location");
        addressDTO.setZipCode("123456");

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        //accountService.addAddress(userId, addressDTO);
        //EasyMock.expectLastCall();

        accountService.addAddress(userId, addressDTO);
        EasyMock.expectLastCall().andThrow(new BizException(BizException.ERROR_CODE_TOKEN_TIMEOUT, "空"));

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        CommonResult commonResult = accountController.addAddress(
                token,"address1","18202740055","123456","location");

        Assert.assertEquals(CommonResult.FAILED_STATUS, commonResult.getResult());
    }

    @Test
    public void testDeleteAddress()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        //accountService.deleteAddress(1);
        //EasyMock.expectLastCall();

        accountService.deleteAddress(1);
        EasyMock.expectLastCall().andThrow(new BizException(BizException.ERROR_CODE_TOKEN_TIMEOUT,"空"));

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        CommonResult commonResult = accountController.deleteAddress(token, 1);

        Assert.assertEquals(CommonResult.FAILED_STATUS, commonResult.getResult());
    }

    @Test
    public void testUpdateAddress()throws Exception{
        long userId = 1;
        String token = "FHJF377FHD7777FFFF33";

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1);
        addressDTO.setName("zhangsan");
        addressDTO.setLocation("location");
        addressDTO.setPhone("18202740055");
        addressDTO.setZipCode("123456");

        EasyMock.expect(tokenService.validate(token)).andReturn(userId);
        accountService.updateAddress(addressDTO,userId);
        EasyMock.expectLastCall();

        control.replay();

        accountController = new AccountController().
                setAccountService(accountService).
                setTokenService(tokenService);

        CommonResult commonResult = accountController.updateAddress(token,1,"zhangsan","18202740055","123456","location");

        Assert.assertEquals(CommonResult.SUCCESS_STATUS,commonResult.getResult());
        Assert.assertEquals("更新地址成功",commonResult.getMessage());
    }
}
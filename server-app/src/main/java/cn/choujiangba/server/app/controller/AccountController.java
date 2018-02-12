package cn.choujiangba.server.app.controller;

import cn.choujiangba.server.app.vo.*;
import cn.choujiangba.server.bal.api.AccountService;
import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/19.
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController{

    private final static Logger logger= LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;
    private TokenService tokenService;

    @Autowired
    public AccountController setAccountService(AccountService accountService){
        this.accountService = accountService;
        return this;
    }

    @Autowired
    public AccountController setTokenService(TokenService tokenService){
        this.tokenService = tokenService;
        return this;
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码，需用Base64加密
     * @param deviceId 设备ID
     * @param latitude 用户经纬度
     * @param longitude 用户经纬度
     * @param city 所在城市
     * @param district 城市区域
     * @return 登录失败：打印错误信息
     *      登录成功：{
                result:"success",
                message:"操作成功",
                auth:{
                    expires_in:"验证参数过期时间",
                    token:"用户验证参数",
                },
                user_detail:{
                    'userId':'',
                    'nickname':'',
                    'avatar_url':'',
                    'balance': ,
                    'gender':'male/female'
                }
            }
     * @throws BizException
     */
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public Object login(@RequestParam(value = "username",required = true)String username,
                             @RequestParam(value = "password",required = true)String password,
                             @RequestParam(value = "deviceId",required = true)String deviceId,
                             @RequestParam(value = "latitude",required = false,defaultValue = "")String latitude,
                             @RequestParam(value = "longitude",required = false,defaultValue = "")String longitude,
                             @RequestParam(value = "city",required = false)String city,
                             @RequestParam(value = "district",required = false)String district)throws BizException{

        //password解码
        byte[] debytes = new Base64().decode(password);
        String decodeStr = new String(debytes);

        logger.info(String.format("login username= %s,password= %s,deviceId= %s"
                ,username,decodeStr,deviceId));

        try{
            TokenDTO tokenDTO = tokenService.authorize(username, decodeStr, city, district, deviceId,
                    httpServletRequest.getRemoteAddr());
            long userId = tokenDTO.getUid();
            double balance = accountService.getMyBalance(userId);
            AccountDTO accountDTO = accountService.getUserInfo(userId);

            Auth auth = new Auth(tokenDTO.getExpireIn(), tokenDTO.getToken());
            UserDetailVO userDetailVO = new UserDetailVO(accountDTO.getUserId(),
                    accountDTO.getNickname(),
                    accountDTO.getAvatarUrl(),
                    balance,
                    accountDTO.getGender()== AccountDTO.Gender.MALE?"male":"female"); //返回用户详细信息

            logger.info(String.format("login success username= %s,deviceId= %s , token= %s",username,deviceId,tokenDTO.getToken()));

            return new LoginResult().setResult(LoginResult.SUCCESS_STATUS).
                    setMessage("登陆成功").setAuth(auth).setUser_detail(userDetailVO);
        }catch (BizException e){
            /**
             * 处理可预见业务逻辑异常
             * */
            if(e.getErrorCode().equals(BizException.ERROR_CODE_USER_NOT_FOUND))
            {
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("用户不存在");
            }

            if(e.getErrorCode().equals(BizException.ERROR_CODE_PASSWORD_ERROR))
            {
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("密码错误");
            }

            logger.error(String.format("Error at login username= %s,", username), e);//若不成功则打印错误信息
            throw e;
        }
    }

    /**
     * 用户注销
     * @param token
     * @return 注销失败：打印出错信息
     *      注销成功：{
                result:true，
                message:'注销成功'
            }
     * @throws BizException
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public CommonResult logout(@RequestParam(value="token",required=true)String token)throws BizException{
        logger.info(String.format("logout token=%s",token));

        tokenService.delete(token); //删除token

        return new CommonResult().setResult(CommonResult.SUCCESS_STATUS).
                setMessage("注销成功");
    }

    /**
     * 用户注册
     * @param username 用户名
     * @param password 用户密码
     * @param nickname 昵称
     * @return {
                    result:"success/failed",
                    message:""
                }
     * @throws BizException
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult register(@RequestParam(value = "username",required = true)String username,
                                 @RequestParam(value = "password",required = true)String password,
                                 @RequestParam(value = "nickname",required = true)String nickname) throws BizException{

        byte[] debytes = new Base64().decode(password);//解码
        String decodeStr = new String(debytes);//将解码的byte数组整理为字符串

        logger.info(String.format("request register ,info : username=%s,password=%s", username, decodeStr));

        try {
            long userId = accountService.register(username, decodeStr,
                    httpServletRequest.getRemoteAddr());

            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUserId(userId);
            accountDTO.setNickname(nickname);
            accountDTO.setGender(AccountDTO.Gender.MALE);
            accountDTO.setAvatarUrl("");

            accountService.updateAccountInfo(accountDTO);

            return new CommonResult().setResult(CommonResult.SUCCESS_STATUS).setMessage("注册成功");
        }catch (BizException e){
            if(e.getErrorCode().equals(BizException.ERROR_CODE_USERNAME_EXISTED))
            {
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).setMessage("用户名重复");
            }

            throw e;
        }
    }

    /**
     * 得到用户资料
     * @param token
     * @return 失败：显示错误信息
     *      成功：{
                    uid:1,
                    nickname:"zy",
                    gender:"male/female",
                    avatar_url:""
                }
     * @throws BizException
     */
    @RequestMapping(value = "/profile/get",method = RequestMethod.POST)
    public UserDetailVO getProfile(@RequestParam(value = "token",required = true)String token)
            throws BizException{
        //验证token返回accountDTO
        long userId = tokenService.validate(token);

        AccountDTO accountDTO = accountService.getUserInfo(userId);

        logger.info(String.format("get profile success userId=%d", userId));

        UserDetailVO userDetailVO = new UserDetailVO(accountDTO.getUserId(),
                accountDTO.getNickname(),
                accountDTO.getAvatarUrl(),
                0,
                accountDTO.getGender() == AccountDTO.Gender.MALE?"male":"female");

        return userDetailVO;
    }

    /**
     * 更新用户信息
     * @param token
     * @param nickname 昵称
     * @param gender
     * @param avatar_url
     * @return {
                        result:"success/failed",
                        message:""
                    }
     * @throws BizException
     */
    @RequestMapping(value = "/profile/update",method = RequestMethod.POST)
    public CommonResult updateProfile(@RequestParam(value = "token",required = true)String token,
                                      @RequestParam(value = "nickname",required = true)String nickname,
                                      @RequestParam(value = "gender",required = true)String gender,
                                      @RequestParam(value = "avatar_url",required = true)String avatar_url)
        throws BizException{

        long userId = tokenService.validate(token);//验证token;

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setNickname(nickname);
        accountDTO.setGender(gender.equals("female") ? AccountDTO.Gender.FEMALE : AccountDTO.Gender.MALE);
        accountDTO.setAvatarUrl(avatar_url);

        accountService.updateAccountInfo(accountDTO);//更新用户资料

        return new CommonResult().setResult(CommonResult.SUCCESS_STATUS).
                    setMessage("更新资料成功");
        
    }

    /**
     * 得到用户金币数
     * @param token
     * @return 失败：显示错误信息
     *      成功：
     *      {
                balance:12
            }
     * @throws BizException
     */
    @RequestMapping(value = "/balance/get",method = RequestMethod.POST)
    public BalanceVO getBalance(@RequestParam(value = "token",required = true)String token)
        throws BizException{

        long userId=tokenService.validate(token);
        double balance = accountService.getMyBalance(userId);

        BalanceVO balanceVO = new BalanceVO();
        balanceVO.setBalance(balance);

        return balanceVO;
    }

    /**
     * 获取用户所有的收货地址
     * @param token
     * @return 获取失败：显示出错信息
     *      获取成功：
     *      [
                {
                    id:1,
                    name:"收货人",
                    phone:"收货人联系方式",
                    zip_code:"邮编",
                    location:"收货人详细地址"
                }
            ]
     * @throws BizException
     */
    @RequestMapping(value = "/address/list",method = RequestMethod.POST)
    public List<AddressVO> listAddress(@RequestParam(value = "token",required = true)String token)
            throws BizException{
        long userId = tokenService.validate(token);

        List<AddressDTO> list  = accountService.listMyAddress(userId);
        //将AddressDTO类型的list转换为AddressVO类型
        List<AddressVO> listVO = new LinkedList<>();

        for(AddressDTO addressDTO : list){
            AddressVO  addressVO = new AddressVO();
            addressVO.setId(addressDTO.getId());
            addressVO.setName(addressDTO.getName());
            addressVO.setPhone(addressDTO.getPhone());
            addressVO.setZip_code(addressDTO.getZipCode());
            addressVO.setLocation(addressDTO.getLocation());

            listVO.add(addressVO);
        }

        logger.info(String.format("list addresses [ userId=%d , size=%d ] ", userId, listVO.size()));

        return listVO;
    }

    /**
     * 用户添加收货地址
     * @param token
     * @param name
     * @param phone 电话
     * @param zip_code 邮编
     * @param location
     * @return 添加失败：显示错误信息
     *      添加成功：
     *          {
                    result:"success/failed",
                    message:"",
                }
     * @throws BizException
     */
    @RequestMapping(value = "/address/add",method = RequestMethod.POST)
    public CommonResult addAddress(@RequestParam(value = "token",required = true)String token,
                                 @RequestParam(value = "name",required = true)String name,
                                 @RequestParam(value = "phone",required = true)String phone,
                                 @RequestParam(value = "zip_code",required = true)String zip_code,
                                 @RequestParam(value = "location",required = true)String location)
            throws BizException{
        CommonResult commonResult = new CommonResult();
        AddressDTO addressDTO = null;
        long userId;


        userId = tokenService.validate(token);

        addressDTO = new AddressDTO();
        addressDTO.setName(name);
        addressDTO.setPhone(phone);
        addressDTO.setZipCode(zip_code);
        addressDTO.setLocation(location);

        accountService.addAddress(userId, addressDTO);

        logger.info(String.format("add address success userId=%d", userId));

        commonResult.setResult(CommonResult.SUCCESS_STATUS);
        commonResult.setMessage("增加成功");

        return commonResult;

    }

    /**
     * 删除收获地址
     * @param token
     * @param address_id
     * @return 删除失败：显示错误信息
     *      删除成功：
     *          {
                    result:"success/failed",
                    message:""
                }
     * @throws BizException
     */
    @RequestMapping(value = "/address/delete",method = RequestMethod.POST)
    public CommonResult deleteAddress(@RequestParam(value = "token",required = true)String token,
                                 @RequestParam(value = "address_id",required = true)long address_id)
            throws BizException{
        CommonResult commonResult = new CommonResult();
        long userId = tokenService.validate(token);

        accountService.deleteAddress(address_id);

        logger.info(String.format("delete address success address_id=%d", address_id));
        logger.info(String.format("userId=%d",userId));

        commonResult.setResult(CommonResult.SUCCESS_STATUS);
        commonResult.setMessage("删除成功");

        return commonResult;
    }

    /**
     * 更新收货地址
     * @param token
     * @param address_id
     * @param name
     * @param phone
     * @param zip_code
     * @param location
     * @return 更新失败：返回错误信息
     *      更新成功：
     *          {
                    result:"success/failed",
                    message:"",
                }
     * @throws BizException
     */
    @RequestMapping(value = "/address/update",method = RequestMethod.POST)
    public CommonResult updateAddress(@RequestParam(value = "token",required = true)String token,
                                      @RequestParam(value = "address_id",required = true)long address_id,
                                      @RequestParam(value = "name",required = true)String name,
                                      @RequestParam(value = "phone",required = true)String phone,
                                      @RequestParam(value = "zip_code",required = true)String zip_code,
                                      @RequestParam(value = "location",required = true)String location)
        throws BizException{

        long userId = tokenService.validate(token);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address_id);
        addressDTO.setName(name);
        addressDTO.setPhone(phone);
        addressDTO.setZipCode(zip_code);
        addressDTO.setLocation(location);

        accountService.updateAddress(addressDTO, userId);

        logger.info(String.format("update address success ,info userId=%d " + addressDTO.toString(), userId));

        return new CommonResult().setResult(CommonResult.SUCCESS_STATUS).setMessage("update success");
    }

    /**
     * 得到用户金币得到 消费记录
     * @param token token
     * @param page 页号
     * @param count 页面数目
     * @return
     *      {
                "content":[
                    {
                        transaction_id:1,
                        timestamp:"发生时间 YYYY/MM/dd hh/mm/ss",
                        desc:"描述",
                        coin:"金币数，可正可负"
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/transactions/list",method = RequestMethod.POST)
    public PageableVO<TransactionsVO> listTransactions(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)throws BizException{
        long userId = tokenService.validate(token);

        //获取我的交易分页列表
        Pageable<TransactionRecordDTO> pageable = accountService.listMyRecord(page, count, userId);

        PageableVO<TransactionsVO> result = new PageableVO<>();

        //规范日期格式
        SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");

        TransactionsVO transactionsVO = null;
        for(TransactionRecordDTO transactionRecordDTO : pageable.getContent()){
            transactionsVO = new TransactionsVO();
            transactionsVO.setTransaction_id(transactionRecordDTO.getTid());
            transactionsVO.setTimestamp(simple.format(transactionRecordDTO.getDate()));
            transactionsVO.setDesc(transactionRecordDTO.getDes());
            transactionsVO.setCoin(transactionRecordDTO.getCoinNum());

            result.getContent().add(transactionsVO);

            logger.info(transactionsVO.toString());
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list transactions success token=%s",token));

        return result;
    }

    /**
     * 给用户添加金币
     * @param token
     * @param value
     * @param desc
     * @return
     * @throws BizException
     */
    @RequestMapping(value="/balance/add",method = RequestMethod.POST)
    public BalanceResult addUserBalance(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "value",required = true)double value,
            @RequestParam(value = "desc",required = true)String desc)throws BizException{
            logger.info(String.format("execute addUserBalance token=%s,value=%f,desc=%s",token,value,desc));
            long userId=tokenService.validate(token);
            accountService.addUserBalance(userId,value,desc);
            logger.info("add user balance success");
            return new BalanceResult().setResult(BalanceResult.SUCCESS_STATUS).setMessage("用户添加金币成功");
    }

}

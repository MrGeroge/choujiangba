package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.dto.AccountDTO;
import cn.choujiangba.server.bal.dto.AddressDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.dto.TransactionRecordDTO;
import cn.choujiangba.server.bal.exception.BizException;

import java.util.Date;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/19.
 */
public interface AccountService {

    /**
     * 重置密码
     *
     * @param username 账户名,非空
     * @param newPassword 新密码，非空
     *
     * @exception BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL,
     *              ERROR_CODE_USER_NOT_FOUND
     *
     * */
    void updatePassword(String username,String newPassword) throws BizException;

    /**
     * 注册
     *
     * @param username 用户名，非空
     * @param password 密码，非空
     * @param ip 用户请求IP，非空
     *
     * @exception BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL
     *              ERROR_CODE_USERNAME_EXISTED
     * */
    long register(String username,String password,String ip) throws BizException;


    /**
     * 更新用户
     *
     * @param info 用户新资料
     *
     * @exception BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL,
     *              ERROR_CODE_INSTANCE_NOT_FOUND
     * */
    void updateAccountInfo(AccountDTO info) throws BizException;

    /**
     * 得到用户资料
     * @param userId
     * @return
     * @throws BizException errorCode包括
     *          ERROR_CODE_FIELD_NOT_NULL,
     *          ERROR_CODE_INSTANCE_NOT_FOUND,
     */
    AccountDTO getUserInfo(long userId)throws BizException;

    /**
     * 列出我的收获地址
     * @param userId
     * @return
     * @throws BizException errorCode包括
     *          ERROR_CODE_FIELD_NOT_NULL,
     *          ERROR_CODE_INSTANCE_NOT_FOUND,
     */
    List<AddressDTO> listMyAddress(long userId)throws BizException;

    /**
     * 添加一个收获地址
     * @param userId
     * @param address
     * @throws BizException errorCode包括
     *          ERROR_CODE_FIELD_NOT_NULL
     */
    void addAddress(long userId,AddressDTO address)throws BizException;

    /**
     * 删除一个收获地址
     * @param addressId
     * @throws BizException errorCode包括
     *            ERROR_CODE_FIELD_NOT_NULL
     */
    void deleteAddress(long addressId)throws BizException;

    /**
     * 更新收获地址
     * @param address
     * @throws BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL
     *              ERROR_CODE_INSTANCE_NOT_FOUND
     *              ERROR_CODE_AUTH_NOT_CORRECT
     */
    void updateAddress(AddressDTO address,long userId)throws BizException;

    /**
     * 得到我的金币数
     * @param userId
     * @return
     * @throws BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL
     */
    double getMyBalance(long userId)throws BizException;

    /**
     * 得到用户基础数据
     * @param
     * @return
     * @throws BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL
     *              ERROR_CODE_INSTANCE_NOT_FOUND
     */
    List<Date> getUserStatistic(Date start,Date finish)throws BizException;

    /**

     * 获取我的交易记录

     * @param page not null 页数

     * @param count notnull 每页多少个

     * @param uid userId

     * @return

     * @throws BizException

     */

    Pageable<TransactionRecordDTO> listMyRecord(int page,int count,long uid)throws BizException;

    /**
     * 增加用户金币
     * @param value not null 需要增加的金币数
     * @param desc  not null 描述信息
     * @return
     * @throws BizException
     */
    void addUserBalance(long uid,double value,String desc)throws BizException;

}

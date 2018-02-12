package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.dto.AccountDTO;
import cn.choujiangba.server.bal.dto.TokenDTO;
import cn.choujiangba.server.bal.exception.BizException;

/**
 * 提供账户认证、Token认证、Token授权撤销
 *
 * Created by shuiyu on 2015/10/19.
 */
public interface TokenService {

    /**
     * 验证token是否有效
     *
     * @param token 系统产生的认证参数,非空
     * @return 用户ID
     * @throws BizException errorCode包括
     *          ERROR_CODE_FIELD_NOT_NULL
     *          ERROR_CODE_TOKEN_TIMEOUT
     */
     long validate(String token)throws BizException;

    /**
     *
     * 认证账户密码,认证通过产生token
     *
     * @param username 用户名,非空
     * @param password 密码（明文）,非空
     * @param city  城市，可空
     * @param district 区域，可空
     * @param deviceId 设备唯一标识，非空
     * @param ip 请求IP ，非空
     * @return
     * @throws BizException errorCode包括
     *          ERROR_CODE_FIELD_NOT_NULL,
     *          ERROR_CODE_PASSWORD_ERROR,
     *          ERROR_CODE_USER_NOT_FOUND
     */
     TokenDTO authorize(String username,
                        String password,
                        String city,//城市
                        String district,//地区
                        String deviceId,//设备号
                        String ip) throws BizException;
    /**
     * 撤销token相关授权
     *
     * @param token 系统产生的认证参数,非空
     *
     * @throws BizException errorCode包括
     *              ERROR_CODE_FIELD_NOT_NULL
     */
     void delete(String token)throws BizException;


}

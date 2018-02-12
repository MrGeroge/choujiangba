package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.exception.BizException;

/**
 * Created by shuiyu on 2015/10/19.
 */
public interface AdminService {
    /**
     * 管理员登录
     * @param username
     * @param password
     * @throws BizException errorCode 包括
     *              ERROR_CODE_FIELD_NOT_NULL
     *              ERROR_CODE_INSTANCE_NOT_FOUND
     *              ERROR_CODE_PASSWORD_ERROR
     */
    void validate(String username,String password)throws BizException;

}

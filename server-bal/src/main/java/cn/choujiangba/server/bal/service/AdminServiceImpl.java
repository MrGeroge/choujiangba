package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.AdminService;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.utils.MD5Util;
import cn.choujiangba.server.dal.api.AdminAccountDao;
import cn.choujiangba.server.dal.po.AdminAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class AdminServiceImpl implements AdminService {

    private static Logger logger= LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminAccountDao adminAccountDao;
    @Autowired
    public AdminServiceImpl setAdminAccountDao(AdminAccountDao adminAccountDao) {
        this.adminAccountDao = adminAccountDao;
        return this;
    }

    @Override
    public void validate(String username, String password) throws BizException{
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"username or password is null");
        logger.info(String.format("admin login username=%s,password=%s",username,password));
        AdminAccount admin = adminAccountDao.findByUsername(username);
        if(admin==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,String.format("there is no such admin username=%s",username));
        if(!MD5Util.encoding(password).equals(admin.getPassword()))
            throw new BizException(BizException.ERROR_CODE_PASSWORD_ERROR,String.format("password is wrong login username=%s,password=%s",username,password));

    }
}

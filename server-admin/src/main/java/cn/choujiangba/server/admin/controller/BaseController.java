package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.ErrorResult;
import cn.choujiangba.server.bal.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hao on 2015/10/19.
 */
public class BaseController {

    private final static Logger logger= LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception e, HttpServletResponse response,
                                   HttpServletRequest request)throws IOException {
        ErrorResult errorResult=new ErrorResult();

        errorResult.setError_code(10001);
        errorResult.setMessage("system error");
        response.setStatus(500);

        logger.error("app",e);

        return errorResult;
    }
}

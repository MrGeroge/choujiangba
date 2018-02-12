package cn.choujiangba.server.app.controller;

import cn.choujiangba.server.app.vo.ErrorResult;
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

        if(e instanceof BizException) {
            BizException b = (BizException) e;

            if(b.getErrorCode() == BizException.ERROR_CODE_TOKEN_TIMEOUT) {
                errorResult.setError_code(20001);
                errorResult.setMessage("token auth failed,cause timeout");
                response.setStatus(401);
            }else if(b.getErrorCode() == BizException.ERROR_CODE_TOKEN_NO_MAPPING){
                errorResult.setError_code(20001);
                errorResult.setMessage("token not found");
                response.setStatus(401);
            }else if(b.getErrorCode() == BizException.ERROR_CODE_FIELD_NOT_NULL ||
                    b.getErrorCode() == BizException.ERROR_CODE_PARAMETER_NOT_VALID) {
                errorResult.setError_code(10003);
                errorResult.setMessage("Params error");
                response.setStatus(400);
            }else if(b.getErrorCode() == BizException.ERROR_CODE_STATUS_WRONG_INPUT ||
                    b.getErrorCode() == BizException.ERROR_CODE_ACTIVITY_STILL_RELY ||
                    b.getErrorCode() == BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR ||
                    b.getErrorCode() == BizException.ERROR_CODE_AUTH_NOT_CORRECT ) {
                errorResult.setError_code(10006);
                errorResult.setMessage("activity error");
                response.setStatus(402);
            }else if(b.getErrorCode() == BizException.ERROR_CODE_INSTANCE_NOT_FOUND){
                errorResult.setError_code(10003);
                errorResult.setMessage("no such data");
                response.setStatus(403);
            }else if(b.getErrorCode() == BizException.ERROR_CODE_NOT_CORRECT_WINNER){
                errorResult.setError_code(10003);
                errorResult.setMessage("you are not correct winner");
                response.setStatus(401);
            }

        }else{
            errorResult.setError_code(10001);
            errorResult.setMessage("System error");
            response.setStatus(500);
        }

        logger.error("app",e);

        return errorResult;
    }
}

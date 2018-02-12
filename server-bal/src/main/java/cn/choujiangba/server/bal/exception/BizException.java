package cn.choujiangba.server.bal.exception;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class BizException extends Exception{
    /**用户名重复*/
    public final static Integer ERROR_CODE_USERNAME_EXISTED=new Integer(2000);
    /**用户不存在*/
    public final static Integer ERROR_CODE_USER_NOT_FOUND=new Integer(2001);
    /**用户密码错误*/
    public final static Integer ERROR_CODE_PASSWORD_ERROR=new Integer(2002);
    /**字段非空异常*/
    public final static Integer ERROR_CODE_FIELD_NOT_NULL = new Integer(2003);
    /**token过期*/
    public final static Integer ERROR_CODE_TOKEN_TIMEOUT = new Integer(2004);
    /**数据库实例未找到*/
    public final static Integer ERROR_CODE_INSTANCE_NOT_FOUND = new Integer(2005);
    /**参数不合法*/
    public final static Integer ERROR_CODE_PARAMETER_NOT_VALID = new Integer(2006);
    /**权限不对*/
    public final static Integer ERROR_CODE_AUTH_NOT_CORRECT = new Integer(2007);
    /**token对应数据不存在*/
    public final static Integer ERROR_CODE_TOKEN_NO_MAPPING = new Integer(2008);
    /**item还有对应活动*/
    public final static Integer ERROR_CODE_ACTIVITY_STILL_RELY = new Integer(2009);
    /**活动还没开始*/
    public final static Integer ERROR_CODE_ACTIVITY_NOT_BEGIN=new Integer(2010);
    /**活动状态不对*/
    public final static Integer ERROR_CODE_ACTIVITY_STATUS_ERROR=new Integer(2011);
    /**活动奖池已满*/
    public final static Integer ERROR_CODE_ACTIVITY_BALANCE_FULL=new Integer(2012);
    /**金币不够*/
    public final static Integer ERROR_CODE_BALANCE_NOT_ENOUGH=new Integer(2013);
    /**状态输入错误*/
    public final static Integer ERROR_CODE_STATUS_WRONG_INPUT=new Integer(2014);
    /**不是活动的获奖者*/
    public final static Integer ERROR_CODE_NOT_CORRECT_WINNER=new Integer(2015);

    private Integer errorCode;

    public BizException(Integer errorCode,String msg){
        super(msg);
        this.errorCode=errorCode;
    }

    public BizException(Integer errorCode,String msg,Throwable e){
        super(msg,e);
        this.errorCode=errorCode;
    }

    public Integer getErrorCode(){
        return this.errorCode;
    }
}

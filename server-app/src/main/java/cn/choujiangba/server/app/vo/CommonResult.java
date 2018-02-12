package cn.choujiangba.server.app.vo;

/**
 *
 * 通用返回数据
 *
 * Author:zhangyu
 * create on 15/9/20.
 */
public class CommonResult{

    public static final String SUCCESS_STATUS="success";
    public static final String FAILED_STATUS="failed";

    private String result;
    private String message;

    public String getResult() {
        return result;
    }

    public CommonResult setResult(String status) {
        this.result = status == null ? "" : status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResult setMessage(String message) {
        this.message = message == null ? "" : message;
        return this;
    }

}

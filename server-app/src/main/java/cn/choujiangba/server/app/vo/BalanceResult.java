package cn.choujiangba.server.app.vo;

/**
 * Created by xinghai on 2015/11/5.
 */
public class BalanceResult {
    public static final String SUCCESS_STATUS="success";
    public static final String FAILED_STATUS="failed";

    private String result;
    private String message;

    public String getResult() {
        return result;
    }

    public BalanceResult setResult(String result) {
        this.result = result == null ? "" : result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BalanceResult setMessage(String message) {
        this.message = message == null ? "" : message;
        return this;
    }
}

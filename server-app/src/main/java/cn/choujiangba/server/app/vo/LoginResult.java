package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/19.
 */
public class LoginResult {

    public static final String SUCCESS_STATUS="success";
    public static final String FAILED_STATUS="failed";

    private String result;
    private String message;

    private Auth auth;
    private UserDetailVO user_detail;

    public String getResult() {
        return result;
    }

    public LoginResult setResult(String result) {
        this.result = result == null ? "" : result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginResult setMessage(String message) {
        this.message = message == null ? "" : message;
        return this;
    }

    public Auth getAuth() {
        return auth;
    }

    public LoginResult setAuth(Auth auth) {
        this.auth = auth;
        return this;
    }

    public UserDetailVO getUser_detail() {
        return user_detail;
    }

    public LoginResult setUser_detail(UserDetailVO user_detail) {
        this.user_detail = user_detail;
        return this;
    }
}

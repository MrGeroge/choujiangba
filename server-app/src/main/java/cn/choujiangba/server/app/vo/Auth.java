package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/19.
 */
public class Auth {

    private long expire_in;
    private String token;

    public Auth(){}

    public Auth(long expire_in, String token){
        this.expire_in = expire_in;
        this.token = token;
    }

    public long getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(long expire_in) {
        this.expire_in = expire_in;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? "" : token;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "expire_in='" + expire_in + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

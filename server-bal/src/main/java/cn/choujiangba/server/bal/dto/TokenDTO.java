package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class TokenDTO {
    private long expireIn;
    private String token;
    private long uid;

    @Override
    public String toString() {
        return "TokenDTO{" +
                "expireIn=" + expireIn +
                ", token='" + token + '\'' +
                ", uid=" + uid +
                '}';
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}

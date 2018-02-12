package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class JoinerDTO {
    private long uid;
    private String url;
    private String nickname;
    private String ip;
    private Date date;
    private double price;

    @Override
    public String toString() {
        return "JoinerDTO{" +
                "uid=" + uid +
                ", url='" + url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

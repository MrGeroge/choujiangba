package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class WinnerDTO {
    private String nickname;
    private Date time;
    private String des;
    private double price;
    private long uid;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WinnerDTO{" +
                "nickname='" + nickname + '\'' +
                ", time=" + time +
                ", des='" + des + '\'' +
                ", price=" + price +
                ", uid=" + uid +
                ", ip='" + ip + '\'' +
                '}';
    }
}

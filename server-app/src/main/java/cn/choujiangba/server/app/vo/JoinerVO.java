package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class JoinerVO {

    private long account_id;
    private String avatar_url;
    private String nickname;
    private String ip;
    private String timestamp;
    private double price;

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url == null ? "" : avatar_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? "" : ip;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? "" : timestamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "JoinerVO{" +
                "account_id=" + account_id +
                ", avatar_url='" + avatar_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", ip='" + ip + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", price=" + price +
                '}';
    }
}

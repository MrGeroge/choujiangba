package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/19.
 */
public class UserDetailVO {

    private long uid;

    private String nickname;
    private String avatar_url;
    private double balance;
    private String gender;

    public UserDetailVO(){}

    public UserDetailVO(long uid,
                         String nickname,
                         String avatar_url,
                         double balance,
                         String gender){
        this.uid = uid;
        this.nickname = nickname;
        this.avatar_url = avatar_url;
        this.balance = balance;
        this.gender = gender;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long userId) {
        this.uid = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url == null ? "" : avatar_url;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? "" : gender;
    }

    @Override
    public String toString() {
        return "UserDetailVO{" +
                "userId=" + uid +
                ", nickname='" + nickname + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", balance=" + balance +
                ", gender='" + gender + '\'' +
                '}';
    }
}

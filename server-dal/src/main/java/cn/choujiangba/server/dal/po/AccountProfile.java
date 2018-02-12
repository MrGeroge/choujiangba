package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_account_profile")
public class AccountProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="uid",unique = true,nullable = false)
    private long uid;
    @Column(name="balance",nullable = false)
    private double balance;
    @Column(name="nickname",nullable = true)
    private String nickname;
    @Column(name="avatar_url",nullable = true)
    private String avatar_url;
    @Column(name="gender",nullable = false)
    private int gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountProfile that = (AccountProfile) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (Double.compare(that.balance, balance) != 0) return false;
        if (gender != that.gender) return false;
        if (!nickname.equals(that.nickname)) return false;
        return avatar_url.equals(that.avatar_url);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + nickname.hashCode();
        result = 31 * result + avatar_url.hashCode();
        result = 31 * result + gender;
        return result;
    }

    @Override
    public String toString() {
        return "AccountProfile{" +
                "id=" + id +
                ", uid=" + uid +
                ", balance=" + balance +
                ", nickname='" + nickname + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", gender=" + gender +
                '}';
    }
}

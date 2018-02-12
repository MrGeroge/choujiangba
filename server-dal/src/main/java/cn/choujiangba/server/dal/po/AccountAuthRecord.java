package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_auth_record")
public class AccountAuthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="token",nullable = false)
    private String token;
    @Column(name="expire_in",nullable = false)
    private Date expire_in;
    @Column(name="auth_time",nullable = false)
    private Date auth_time;
    @Column(name="device_id",nullable = false)
    private String device_id;
    @Column(name="ip",nullable = false)
    private String ip;
    @Column(name="city",nullable = true)
    private String city;
    @Column(name="district",nullable = true)
    private String district;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(Date expire_in) {
        this.expire_in = expire_in;
    }

    public Date getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(Date auth_time) {
        this.auth_time = auth_time;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountAuthRecord that = (AccountAuthRecord) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (!token.equals(that.token)) return false;
        if (!expire_in.equals(that.expire_in)) return false;
        if (!auth_time.equals(that.auth_time)) return false;
        if (!device_id.equals(that.device_id)) return false;
        if (!ip.equals(that.ip)) return false;
        if (!city.equals(that.city)) return false;
        return district.equals(that.district);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + token.hashCode();
        result = 31 * result + expire_in.hashCode();
        result = 31 * result + auth_time.hashCode();
        result = 31 * result + device_id.hashCode();
        result = 31 * result + ip.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + district.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccountAuthRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", token='" + token + '\'' +
                ", expire_in=" + expire_in +
                ", auth_time=" + auth_time +
                ", device_id='" + device_id + '\'' +
                ", ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}

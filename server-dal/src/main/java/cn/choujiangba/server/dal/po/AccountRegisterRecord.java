package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_account_register_record")
public class AccountRegisterRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="create_at",nullable = false)
    private Date create_at;
    @Column(name="ip",nullable = false)
    private String ip;

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

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountRegisterRecord that = (AccountRegisterRecord) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (!create_at.equals(that.create_at)) return false;
        return ip.equals(that.ip);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + create_at.hashCode();
        result = 31 * result + ip.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccountRegisterRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", create_at=" + create_at +
                ", ip='" + ip + '\'' +
                '}';
    }
}

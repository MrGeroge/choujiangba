package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_admin_account")
public class AdminAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="username",unique = true,nullable=false)
    private String username;
    @Column(name="password",nullable=false)
    private String password;
    @Column(name="email",nullable = true)
    private String email;
    @Column(name="last_login_time",nullable = false)
    private Date last_login_time;
    @Column(name="ip",nullable = false)
    private String ip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
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

        AdminAccount that = (AdminAccount) o;

        if (id != that.id) return false;
        if (!username.equals(that.username)) return false;
        if (!password.equals(that.password)) return false;
        if (!email.equals(that.email)) return false;
        if (!last_login_time.equals(that.last_login_time)) return false;
        return ip.equals(that.ip);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + last_login_time.hashCode();
        result = 31 * result + ip.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", last_login_time=" + last_login_time +
                ", ip='" + ip + '\'' +
                '}';
    }
}

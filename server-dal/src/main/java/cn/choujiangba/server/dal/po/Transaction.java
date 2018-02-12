package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="create_at",nullable = false)
    private Date create_at;
    @Column(name="description",nullable = false)
    private String desc;
    @Column(name="coin_num",nullable = false)
    private double coin_num;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getCoin_num() {
        return coin_num;
    }

    public void setCoin_num(double coin_num) {
        this.coin_num = coin_num;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (coin_num != that.coin_num) return false;
        if (!create_at.equals(that.create_at)) return false;
        return desc.equals(that.desc);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        long temp;
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + create_at.hashCode();
        result = 31 * result + desc.hashCode();
        temp = Double.doubleToLongBits(coin_num);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", uid=" + uid +
                ", create_at=" + create_at +
                ", desc='" + desc + '\'' +
                ", coin_num=" + coin_num +
                '}';
    }
}

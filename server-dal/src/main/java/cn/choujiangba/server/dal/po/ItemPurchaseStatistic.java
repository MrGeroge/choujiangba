package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/21.
 */
@Entity
@Table(name="tb_item_purchase_statistics")
public class ItemPurchaseStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long item_id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="pay",nullable = false)
    private double pay;
    @Column(name="buy_time",nullable = false)
    private Date buy_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public Date getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(Date buy_time) {
        this.buy_time = buy_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPurchaseStatistic that = (ItemPurchaseStatistic) o;

        if (id != that.id) return false;
        if (item_id != that.item_id) return false;
        if (uid != that.uid) return false;
        if (pay != that.pay) return false;
        return buy_time.equals(that.buy_time);

    }

    @Override
    public int hashCode() {
        long temp;
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (item_id ^ (item_id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        temp = Double.doubleToLongBits(pay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + buy_time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemPurchaseStatistic{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", uid=" + uid +
                ", pay=" + pay +
                ", buy_time=" + buy_time +
                '}';
    }
}

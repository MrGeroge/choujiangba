package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ItemPurchaseStatisticDTO {
    private long itemId;
    private long uid;
    private double pay;
    private Date date;
    private long id;

    @Override
    public String toString() {
        return "ItemPurchaseStatisticDTO{" +
                "itemId=" + itemId +
                ", uid=" + uid +
                ", pay=" + pay +
                ", date=" + date +
                ", id=" + id +
                '}';
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

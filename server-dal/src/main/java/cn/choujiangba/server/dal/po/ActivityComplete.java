package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/25.
 */
@Entity
@Table(name="tb_activity_complete")
public class ActivityComplete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long itemId;
    @Column(unique = true,name="activity_id",nullable = false)
    private long activityId;
    @Column(name="price",nullable = false)
    private double price;
    @Column(name="finish_time",nullable = false)
    private Date finishTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityComplete that = (ActivityComplete) o;

        if (id != that.id) return false;
        if (itemId != that.itemId) return false;
        if (activityId != that.activityId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        return finishTime.equals(that.finishTime);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + finishTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActivityComplete{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", activityId=" + activityId +
                ", price=" + price +
                ", finishTime=" + finishTime +
                '}';
    }
}

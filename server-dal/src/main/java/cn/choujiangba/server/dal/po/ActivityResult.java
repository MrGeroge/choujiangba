package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/25.
 */
@Entity
@Table(name="tb_activity_result")
public class ActivityResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="activity_id",nullable = false)
    private long activityId;
    @Column(name="item_id",nullable = false)
    private long itemId;
    @Column(name="description",nullable = false)
    private String desc;
    @Column(name="price",nullable=false)
    private double price;
    @Column(name="reward_time",nullable=false)
    private Date rewardTime;
    @Column(name="winner_id",nullable = true)
    private long winnerId=0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityResult)) return false;

        ActivityResult that = (ActivityResult) o;

        if (id != that.id) return false;
        if (activityId != that.activityId) return false;
        if (itemId != that.itemId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (winnerId != that.winnerId) return false;
        if (!desc.equals(that.desc)) return false;
        return rewardTime.equals(that.rewardTime);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + desc.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + rewardTime.hashCode();
        result = 31 * result + (int) (winnerId ^ (winnerId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ActivityResult{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", itemId=" + itemId +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", rewardTime=" + rewardTime +
                ", winnerId=" + winnerId +
                '}';
    }
}

package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/25.
 */
@Entity
@Table(name="tb_activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long itemId;
    @Column(name="reward_interval",nullable = false)
    private long rewardInterval;
    @Column(name="price",nullable = false)
    private double price;
    @Column(name="status",nullable = false)
    private int status;
    @Column(name="winner_id",nullable = true)
    private long winnerId=0;

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

    public long getRewardInterval() {
        return rewardInterval;
    }

    public void setRewardInterval(long rewardInterval) {
        this.rewardInterval = rewardInterval;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!(o instanceof Activity)) return false;

        Activity activity = (Activity) o;

        if (id != activity.id) return false;
        if (itemId != activity.itemId) return false;
        if (rewardInterval != activity.rewardInterval) return false;
        if (Double.compare(activity.price, price) != 0) return false;
        if (status != activity.status) return false;
        return winnerId == activity.winnerId;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (rewardInterval ^ (rewardInterval >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + status;
        result = 31 * result + (int) (winnerId ^ (winnerId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", rewardInterval=" + rewardInterval +
                ", price=" + price +
                ", status=" + status +
                ", winnerId=" + winnerId +
                '}';
    }
}

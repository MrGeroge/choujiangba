package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/11/7.
 */
@Entity
@Table(name="tb_activity_delivery")
public class ActivityDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="itemId",nullable = false)
    private long itemId;
    @Column(name="activityId",nullable = false)
    private long activityId;
    @Column(name="express_num",nullable = false)
    private String expressNum;
    @Column(name="create_at",nullable = false)
    private Date createAt;
    @Column(name="received",nullable = false)
    private boolean received;

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

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityDelivery that = (ActivityDelivery) o;

        if (id != that.id) return false;
        if (itemId != that.itemId) return false;
        if (activityId != that.activityId) return false;
        if (received != that.received) return false;
        if (!expressNum.equals(that.expressNum)) return false;
        return createAt.equals(that.createAt);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        result = 31 * result + expressNum.hashCode();
        result = 31 * result + createAt.hashCode();
        result = 31 * result + (received ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityDelivery{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", activityId=" + activityId +
                ", expressNum='" + expressNum + '\'' +
                ", createAt=" + createAt +
                ", received=" + received +
                '}';
    }
}

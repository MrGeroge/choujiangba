package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/25.
 */
@Entity
@Table(name="tb_activity_join_record")
public class ActivityJoinRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long itemId;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="activity_id",nullable = false)
    private long activityId;
    @Column(name="ip",nullable = false)
    private String ip;
    @Column(name="join_time",nullable = false)
    private Date joinTime;
    @Column(name="city",nullable = true)
    private String city;
    @Column(name="district",nullable = true)
    private String district;
    @Column(name="price",nullable = false)
    private double price;

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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityJoinRecord that = (ActivityJoinRecord) o;

        if (id != that.id) return false;
        if (itemId != that.itemId) return false;
        if (uid != that.uid) return false;
        if (activityId != that.activityId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (!ip.equals(that.ip)) return false;
        if (!joinTime.equals(that.joinTime)) return false;
        if (!city.equals(that.city)) return false;
        return district.equals(that.district);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        result = 31 * result + ip.hashCode();
        result = 31 * result + joinTime.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + district.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ActivityJoinRecord{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", uid=" + uid +
                ", activityId=" + activityId +
                ", ip='" + ip + '\'' +
                ", joinTime=" + joinTime +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", price=" + price +
                '}';
    }
}

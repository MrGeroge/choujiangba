package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/21.
 */
@Entity
@Table(name="tb_item_view_statistics")
public class ItemViewStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long item_id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="view_time",nullable = false)
    private long view_time;
    @Column(name="ip",nullable = false)
    private String ip;
    @Column(name="city",nullable = true)
    private String city;
    @Column(name="district",nullable = true)
    private String district;
    @Column(name="latitiude",nullable = true)
    private double latitiude;
    @Column(name="longitude",nullable = true)
    private double longitude;

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

    public long getView_time() {
        return view_time;
    }

    public void setView_time(long view_time) {
        this.view_time = view_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemViewStatistic that = (ItemViewStatistic) o;

        if (id != that.id) return false;
        if (item_id != that.item_id) return false;
        if (uid != that.uid) return false;
        if (view_time != that.view_time) return false;
        if (Double.compare(that.latitiude, latitiude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (!ip.equals(that.ip)) return false;
        if (!city.equals(that.city)) return false;
        return district.equals(that.district);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (item_id ^ (item_id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + (int) (view_time ^ (view_time >>> 32));
        result = 31 * result + ip.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + district.hashCode();
        temp = Double.doubleToLongBits(latitiude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ItemViewStatistic{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", uid=" + uid +
                ", view_time=" + view_time +
                ", ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", latitiude=" + latitiude +
                ", longitude=" + longitude +
                '}';
    }
}

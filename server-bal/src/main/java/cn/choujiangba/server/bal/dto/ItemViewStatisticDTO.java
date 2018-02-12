package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ItemViewStatisticDTO {
    private long date;
    private String ip;
    private String city;
    private String district;
    private double longitude;
    private double latitude;
    private long uid;
    private long id;
    private long itemId;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

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

    @Override
    public String toString() {
        return "ItemViewStatisticDTO{" +
                "date=" + date +
                ", ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", uid=" + uid +
                ", id=" + id +
                ", itemId=" + itemId +
                '}';
    }
}


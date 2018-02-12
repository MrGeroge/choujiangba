package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/19.
 */
@Entity
@Table(name="tb_delivery_address")
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="address_name",nullable = false)
    private String name;
    @Column(name="phone",nullable = false)
    private String phone;
    @Column(name="zip_code",nullable = false)
    private String zip_code;
    @Column(name="location",nullable = false)
    private String location;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryAddress that = (DeliveryAddress) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (!name.equals(that.name)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!zip_code.equals(that.zip_code)) return false;
        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + zip_code.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "id=" + id +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

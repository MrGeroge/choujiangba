package cn.choujiangba.server.bal.dto;

import com.sun.jndi.cosnaming.IiopUrl;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class AddressDTO {
    private long id;
    private String name;
    private String phone;
    private String zipCode;
    private String location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressDTO(){}
    public AddressDTO(String name,String phone,String zipCode,String location){
        this.name=name;
        this.phone=phone;
        this.zipCode=zipCode;
        this.location=location;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;

        AddressDTO that = (AddressDTO) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!zipCode.equals(that.zipCode)) return false;
        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}

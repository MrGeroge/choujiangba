package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/19.
 */
public class AddressVO {

    private long id;

    private String name;
    private String phone;
    private String zip_code;
    private String location;

    public AddressVO(){}

    public AddressVO(long id,
                     String name,
                     String phone,
                     String zip_code,
                     String location){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.zip_code = zip_code;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" :phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zipCode) {
        this.zip_code = zipCode == null ? "" : zipCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? "" : location;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zip_code + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

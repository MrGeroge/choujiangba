package cn.choujiangba.server.app.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/25.
 */
public class ItemDetailVO {

    private long id;
    private String name;
    private double price;
    private String detail;
    private List<String> img_urls = new LinkedList<>();

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? "" : detail;
    }

    public List<String> getImg_urls() {
        return img_urls;
    }

    @Override
    public String toString() {
        return "ItemDetailVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", img_urls=" + img_urls +
                '}';
    }
}

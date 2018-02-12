package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/21.
 */
@Entity
@Table(name="tb_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_name",nullable = false)
    private String name;
    @Column(name="price",nullable = false)
    private int price;
    @Lob
    @Column(name="detail",nullable = false)
    private String detail;
    @Column(name="thumbnail_url",nullable = false)
    private String thumbnail_url;
    @Lob
    @Column(name="desc_img_urls",nullable = false)
    private String desc_img_urls;
    @Column(name="property",nullable = false)
    private String property;

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
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getDesc_img_urls() {
        return desc_img_urls;
    }

    public void setDesc_img_urls(String desc_img_urls) {
        this.desc_img_urls = desc_img_urls;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (price != item.price) return false;
        if (!name.equals(item.name)) return false;
        if (!detail.equals(item.detail)) return false;
        if (!thumbnail_url.equals(item.thumbnail_url)) return false;
        if (!desc_img_urls.equals(item.desc_img_urls)) return false;
        return property.equals(item.property);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + price;
        result = 31 * result + detail.hashCode();
        result = 31 * result + thumbnail_url.hashCode();
        result = 31 * result + desc_img_urls.hashCode();
        result = 31 * result + property.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                ", desc_img_urls='" + desc_img_urls + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}

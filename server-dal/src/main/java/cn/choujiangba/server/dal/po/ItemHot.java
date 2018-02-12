package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/25.
 */
@Entity
@Table(name="tb_item_hot")
public class ItemHot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="banner_url",nullable = false)
    private String bannerUrl;
    @Column(name="item_id",nullable = false)
    private long itemId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemHot itemHot = (ItemHot) o;

        if (id != itemHot.id) return false;
        if (itemId != itemHot.itemId) return false;
        return bannerUrl.equals(itemHot.bannerUrl);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + bannerUrl.hashCode();
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ItemHot{" +
                "id=" + id +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", itemId=" + itemId +
                '}';
    }
}

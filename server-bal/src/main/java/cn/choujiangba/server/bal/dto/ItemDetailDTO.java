package cn.choujiangba.server.bal.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/21.
 */
public class ItemDetailDTO {
    private long itemId;
    private String name;
    private int price;
    private String detail;
    private List<String> property= new LinkedList<>();
    private String thumbnailUrl;
    private List<String> descImgUrl=new LinkedList<>();

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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

    public List<String> getProperty() {
        return property;
    }

    public void setProperty(List<String> property) {
        this.property = property;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<String> getDescImgUrl() {
        return descImgUrl;
    }

    @Override
    public String toString() {
        return "ItemDetailDTO{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", property=" + property +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", descImgUrl=" + descImgUrl +
                '}';
    }
}

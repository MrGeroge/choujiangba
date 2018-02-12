package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class HotItemDTO {
    private long id;
    private long itemId;
    private String banner;
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "HotItemDTO{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", banner='" + banner + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}

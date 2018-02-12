package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/21.
 */
public class ItemSimpleDTO {
    private long itemId;
    private long activityNum;
    private String name;
    private String thumbnailUrl;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(long activityNum) {
        this.activityNum = activityNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "ItemSimpleDTO{" +
                "itemId=" + itemId +
                ", activityNum=" + activityNum +
                ", name='" + name + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSimpleDTO)) return false;

        ItemSimpleDTO that = (ItemSimpleDTO) o;

        if (itemId != that.itemId) return false;
        if (activityNum != that.activityNum) return false;
        if (!name.equals(that.name)) return false;
        return thumbnailUrl.equals(that.thumbnailUrl);

    }

    @Override
    public int hashCode() {
        int result = (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (activityNum ^ (activityNum >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + thumbnailUrl.hashCode();
        return result;
    }
}

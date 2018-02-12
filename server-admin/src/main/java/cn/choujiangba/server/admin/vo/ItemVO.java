package cn.choujiangba.server.admin.vo;

/**
 * Created by hao on 2015/10/21.
 */
public class ItemVO {

    private long item_id;

    private String activities_num;
    private String item_name;
    private String item_thumbnail_url;

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getActivities_num() {
        return activities_num;
    }

    public void setActivities_num(String activities_num) {
        this.activities_num = activities_num;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_thumbnail_url() {
        return item_thumbnail_url;
    }

    public void setItem_thumbnail_url(String item_thumbnail_url) {
        this.item_thumbnail_url = item_thumbnail_url;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "item_id=" + item_id +
                ", activities_num='" + activities_num + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_thumbnail_url='" + item_thumbnail_url + '\'' +
                '}';
    }
}

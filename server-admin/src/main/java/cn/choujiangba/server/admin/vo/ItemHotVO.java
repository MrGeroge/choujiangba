package cn.choujiangba.server.admin.vo;

/**
 * Created by hao on 2015/10/26.
 */
public class ItemHotVO {

    private long id;
    private long item_id;
    private String item_name;
    private String banner_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    @Override
    public String toString() {
        return "ItemHotVO{" +
                "item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", banner_url='" + banner_url + '\'' +
                '}';
    }
}

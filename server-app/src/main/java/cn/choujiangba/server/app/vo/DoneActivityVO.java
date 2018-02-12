package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class DoneActivityVO {

    private long activity_id;
    private String item_name;
    private String item_thumbnail_url;
    private long activity_finish_interval;

    public long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(long activity_id) {
        this.activity_id = activity_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name == null ? "" : item_name;
    }

    public String getItem_thumbnail_url() {
        return item_thumbnail_url;
    }

    public void setItem_thumbnail_url(String item_thumbnail_url) {
        this.item_thumbnail_url = item_thumbnail_url == null ? "" : item_thumbnail_url;
    }

    public long getActivity_finish_interval() {
        return activity_finish_interval;
    }

    public void setActivity_finish_interval(long activity_finish_interval) {
        this.activity_finish_interval = activity_finish_interval;
    }

    @Override
    public String toString() {
        return "DoneActivityVO{" +
                "activity_id=" + activity_id +
                ", item_name='" + item_name + '\'' +
                ", item_thumbnail_url='" + item_thumbnail_url + '\'' +
                ", activity_finish_interval='" + activity_finish_interval + '\'' +
                '}';
    }
}

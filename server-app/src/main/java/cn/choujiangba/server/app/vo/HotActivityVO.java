package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class HotActivityVO {

    private long activity_id;
    private String banner_url;

    public long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(long activity_id) {
        this.activity_id = activity_id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url == null ? "" : banner_url;
    }

    @Override
    public String toString() {
        return "HotActivityVO{" +
                "activity_id=" + activity_id +
                ", banner_url='" + banner_url + '\'' +
                '}';
    }
}

package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class UnderwayActivityVO {

    private long activity_id;
    private int activity_status;
    private String item_name;
    private String item_thumbnail_url;
    private double activity_price;
    private double activity_real;
    private double activity_percentage;
    private boolean is_winner;

    public long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(long activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(int activity_status) {
        this.activity_status = activity_status;
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

    public double getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(double activity_price) {
        this.activity_price = activity_price;
    }

    public double getActivity_real() {
        return activity_real;
    }

    public void setActivity_real(double activity_real) {
        this.activity_real = activity_real;
    }

    public double getActivity_percentage() {
        return activity_percentage;
    }

    public void setActivity_percentage(double activity_percentage) {
        this.activity_percentage = activity_percentage;
    }

    public boolean getIs_winner() {
        return is_winner;
    }

    public void setIs_winner(boolean is_winner) {
        this.is_winner = is_winner;
    }

    @Override
    public String toString() {
        return "UnderwayActivityVO{" +
                "activity_id=" + activity_id +
                ", item_name='" + item_name + '\'' +
                ", item_thumbnail_url='" + item_thumbnail_url + '\'' +
                ", activity_price='" + activity_price + '\'' +
                ", activity_real='" + activity_real + '\'' +
                ", activity_percentage='" + activity_percentage + '\'' +
                '}';
    }
}

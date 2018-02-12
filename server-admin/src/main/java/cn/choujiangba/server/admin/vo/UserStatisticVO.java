package cn.choujiangba.server.admin.vo;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class UserStatisticVO {
    private String timestamp;
    private int increase;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }
}

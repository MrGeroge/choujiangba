package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/21.
 */
public class ItemStatisticDTO {
    private long activityNum;
    private long viewNum;
    private long replyNum;

    public long getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(long activityNum) {
        this.activityNum = activityNum;
    }

    public long getViewNum() {
        return viewNum;
    }

    public void setViewNum(long viewNum) {
        this.viewNum = viewNum;
    }

    public long getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(long replyNum) {
        this.replyNum = replyNum;
    }

    @Override
    public String toString() {
        return "ItemStatisticDTO{" +
                "activityNum=" + activityNum +
                ", viewNum=" + viewNum +
                ", replyNum=" + replyNum +
                '}';
    }
}

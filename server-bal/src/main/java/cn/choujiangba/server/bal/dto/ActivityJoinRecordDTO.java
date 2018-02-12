package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ActivityJoinRecordDTO {
    private long uid;
    private boolean joined;
    private double joinPrice;
    private long joinId;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public double getJoinPrice() {
        return joinPrice;
    }

    public void setJoinPrice(double joinPrice) {
        this.joinPrice = joinPrice;
    }

    public long getJoinId() {
        return joinId;
    }

    @Override
    public String toString() {
        return "ActivityJoinRecordDTO{" +
                "uid=" + uid +
                ", joined=" + joined +
                ", joinPrice=" + joinPrice +
                ", joinId=" + joinId +
                '}';
    }

    public void setJoinId(long joinId) {

        this.joinId = joinId;
    }
}

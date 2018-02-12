package cn.choujiangba.server.bal.dto;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/23.
 */
public class TransactionRecordDTO {
    private Date date;
    private long uid;
    private String des;
    private double coinNum;
    private long tid;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(double coinNum) {
        this.coinNum = coinNum;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "TransactionRecordDTO{" +
                "date=" + date +
                ", uid=" + uid +
                ", des='" + des + '\'' +
                ", coinNum=" + coinNum +
                ", tid=" + tid +
                '}';
    }
}

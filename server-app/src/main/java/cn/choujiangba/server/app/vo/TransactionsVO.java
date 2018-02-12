package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class TransactionsVO {

    private long transaction_id;
    private String timestamp;
    private String desc;
    private double coin;

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? "" : timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? "" : desc;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "TransactionsVO{" +
                "transaction_id=" + transaction_id +
                ", timestamp='" + timestamp + '\'' +
                ", desc='" + desc + '\'' +
                ", coin='" + coin + '\'' +
                '}';
    }
}

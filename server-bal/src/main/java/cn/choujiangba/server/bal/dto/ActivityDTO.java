package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ActivityDTO {
    public enum Status{
        NOT_START {
            @Override
            public int getNum() {
                return 0;
            }
        },IN_PROGRESS {
            @Override
            public int getNum() {
                return 1;
            }
        },WILL_ANNOUNCE {
            @Override
            public int getNum() {
                return 2;
            }
        },WILL_SEND {
            @Override
            public int getNum() {
                return 3;
            }
        };
        public abstract int getNum();
    }
    private long rewardInterval;
    private Status status;
    private long winnerId;
    private String itemThumbnailUrl;
    private double realPrice;
    private double percent;
    private long activityId;
    private ItemDetailDTO itemDetail;
    private double price;
    private WinnerDTO winner;
    private double scale;

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public WinnerDTO getWinner() {
        return winner;
    }

    public void setWinner(WinnerDTO winner) {
        this.winner = winner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemDetailDTO getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetailDTO itemDetail) {
        this.itemDetail = itemDetail;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getItemThumbnailUrl() {
        return itemThumbnailUrl;
    }

    public void setItemThumbnailUrl(String itemThumbnailUrl) {
        this.itemThumbnailUrl = itemThumbnailUrl;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
    public long getRewardInterval() {
        return rewardInterval;
    }

    public void setRewardInterval(long rewardInterval) {
        this.rewardInterval = rewardInterval;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getWinnerId() {
        return winnerId;
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
                "rewardInterval=" + rewardInterval +
                ", status=" + status +
                ", winnerId=" + winnerId +
                ", itemThumbnailUrl='" + itemThumbnailUrl + '\'' +
                ", realPrice=" + realPrice +
                ", percent='" + percent + '\'' +
                ", activityId=" + activityId +
                ", itemDetail=" + itemDetail +
                ", price=" + price +
                ", winner=" + winner +
                '}';
    }

    public void setWinnerId(long winnerId) {

        this.winnerId = winnerId;
    }
}

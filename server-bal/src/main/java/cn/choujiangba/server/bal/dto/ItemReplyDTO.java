package cn.choujiangba.server.bal.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ItemReplyDTO {
    private Date date;
    private String comment;
    private List<String> urls = new LinkedList<>();
    private long uid;
    private long itemId;
    private long activityId;
    private String itemName;
    private String nickName;
    private String AvatarUrl;

    @Override
    public String toString() {
        return "ItemReplyDTO{" +
                "date=" + date +
                ", comment='" + comment + '\'' +
                ", urls=" + urls +
                ", uid=" + uid +
                ", itemId=" + itemId +
                ", activityId=" + activityId +
                ", itemName='" + itemName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", AvatarUrl='" + AvatarUrl + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}

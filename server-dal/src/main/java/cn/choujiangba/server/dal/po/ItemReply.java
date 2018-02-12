package cn.choujiangba.server.dal.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghai on 2015/10/21.
 */
@Entity
@Table(name="tb_item_reply")
public class ItemReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="item_id",nullable = false)
    private long itemId;
    @Column(name="activity_id",nullable = false)
    private long activityId;
    @Column(name="uid",nullable = false)
    private long uid;
    @Column(name="create_at",nullable = false)
    private Date create_at;
    @Column(name="text_content",nullable = false)
    private String text_content;
    @Lob
    @Column(name="img_urls",nullable = false)
    private String img_urls;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public String getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(String img_urls) {
        this.img_urls = img_urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemReply itemReply = (ItemReply) o;

        if (id != itemReply.id) return false;
        if (itemId != itemReply.itemId) return false;
        if (activityId != itemReply.activityId) return false;
        if (uid != itemReply.uid) return false;
        if (!create_at.equals(itemReply.create_at)) return false;
        if (!text_content.equals(itemReply.text_content)) return false;
        return img_urls.equals(itemReply.img_urls);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (activityId ^ (activityId >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + create_at.hashCode();
        result = 31 * result + text_content.hashCode();
        result = 31 * result + img_urls.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemReply{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", activityId=" + activityId +
                ", uid=" + uid +
                ", create_at=" + create_at +
                ", text_content='" + text_content + '\'' +
                ", img_urls='" + img_urls + '\'' +
                '}';
    }
}

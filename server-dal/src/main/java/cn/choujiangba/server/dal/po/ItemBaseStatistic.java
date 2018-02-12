package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/21.
 */
@Entity
@Table(name="tb_item_base_statistics")
public class ItemBaseStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true,name="item_id",nullable = false)
    private long itemId;
    @Column(name="published_activity_num",nullable = false)
    private long published_activity_num;
    @Column(name="view_num",nullable = false)
    private long view_num;
    @Column(name="reply_num",nullable = false)
    private long reply_num;

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

    public long getPublished_activity_num() {
        return published_activity_num;
    }

    public void setPublished_activity_num(long published_activity_num) {
        this.published_activity_num = published_activity_num;
    }

    public long getView_num() {
        return view_num;
    }

    public void setView_num(long view_num) {
        this.view_num = view_num;
    }

    public long getReply_num() {
        return reply_num;
    }

    public void setReply_num(long reply_num) {
        this.reply_num = reply_num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBaseStatistic that = (ItemBaseStatistic) o;

        if (id != that.id) return false;
        if (itemId != that.itemId) return false;
        if (published_activity_num != that.published_activity_num) return false;
        if (view_num != that.view_num) return false;
        return reply_num == that.reply_num;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (published_activity_num ^ (published_activity_num >>> 32));
        result = 31 * result + (int) (view_num ^ (view_num >>> 32));
        result = 31 * result + (int) (reply_num ^ (reply_num >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ItemBaseStatistic{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", published_activity_num=" + published_activity_num +
                ", view_num=" + view_num +
                ", reply_num=" + reply_num +
                '}';
    }
}

package cn.choujiangba.server.app.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/28.
 */
public class ReplyVO {

    private long account_id;
    private String avatar_url;
    private String nickname;
    private String create_at;
    private String item_name;
    private long activity_id;
    private String content;
    private List<String> imgs = new LinkedList<>();

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url == null ? "" : avatar_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at == null ? "" : create_at;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name == null ? "" : item_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(long activity_id) {
        this.activity_id = activity_id;
    }

    @Override
    public String toString() {
        return "ReplyVO{" +
                "account_id=" + account_id +
                ", avatar_url='" + avatar_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", create_at='" + create_at + '\'' +
                ", item_name='" + item_name + '\'' +
                ", activity_id=" + activity_id +
                ", content='" + content + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}

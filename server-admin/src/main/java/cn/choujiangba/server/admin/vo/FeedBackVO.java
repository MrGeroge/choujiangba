package cn.choujiangba.server.admin.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class FeedBackVO {
    private long feedback_id;
    private String content;
    private String contact;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private List<String> imgs=new LinkedList<>();

    public long getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(long feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}

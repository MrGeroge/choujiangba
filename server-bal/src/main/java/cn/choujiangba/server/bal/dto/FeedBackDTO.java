package cn.choujiangba.server.bal.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class FeedBackDTO {
    public enum Status{
        NOT_HANDLE{public int getNum(){return 0;}},
        HANDLING{public int getNum(){return 1;}},
        SOLVED{public int getNum(){return 2;}},
        IGNORE{public int getNum(){return 3;}},
        ALL{public int getNum(){return 4;}};
        public abstract int getNum();
    }
    private long feedbackId;
    private String content;
    private String contact;
    private List<String> imgs  = new LinkedList<>();
    private Status status;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public String getContent() {
        return content;
    }

    public List<String> getImgs() {
        return imgs;
    }


    public Status getStatus() {

        return status;
    }

    @Override
    public String toString() {
        return "FeedBackDTO{" +
                "feedbackId=" + feedbackId +
                ", content='" + content + '\'' +
                ", contact='" + contact + '\'' +
                ", imgs=" + imgs +
                ", status=" + status +
                '}';
    }
}

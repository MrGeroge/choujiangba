package cn.choujiangba.server.dal.po;

import javax.persistence.*;

/**
 * Created by xinghai on 2015/10/20.
 */
@Entity
@Table(name="tb_feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Lob
    @Column(name="content",nullable = false)
    private String content;
    @Column(name="contact",nullable = false)
    private String contact;
    @Lob
    @Column(name="img_urls",nullable = true)
    private String img_urls;
    @Column(name="status",nullable = false)
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(String img_urls) {
        this.img_urls = img_urls;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (id != feedback.id) return false;
        if (status != feedback.status) return false;
        if (!content.equals(feedback.content)) return false;
        if (!contact.equals(feedback.contact)) return false;
        return img_urls.equals(feedback.img_urls);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + content.hashCode();
        result = 31 * result + contact.hashCode();
        result = 31 * result + img_urls.hashCode();
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", contact='" + contact + '\'' +
                ", img_urls='" + img_urls + '\'' +
                ", status=" + status +
                '}';
    }
}

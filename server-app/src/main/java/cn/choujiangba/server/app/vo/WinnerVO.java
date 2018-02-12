package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/25.
 */
public class WinnerVO {

    private String nickname;
    private String time;
    private String desc;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? "" : time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? "" : desc;
    }

    @Override
    public String toString() {
        return "WinnerVO{" +
                "nickname='" + nickname + '\'' +
                ", time='" + time + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

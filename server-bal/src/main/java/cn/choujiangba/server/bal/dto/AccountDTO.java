package cn.choujiangba.server.bal.dto;

/**
 * Created by shuiyu on 2015/10/19.
 */
public class AccountDTO {
    public enum Gender  {
        MALE{public String getGender(){return "男";}},
        FEMALE{public String getGender(){return "女";}};
        public abstract String getGender();
    }

    private long userId;//用户id
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDTO)) return false;

        AccountDTO that = (AccountDTO) o;

        if (userId != that.userId) return false;
        if (!nickname.equals(that.nickname)) return false;
        if (!avatarUrl.equals(that.avatarUrl)) return false;
        return gender == that.gender;

    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + nickname.hashCode();
        result = 31 * result + avatarUrl.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                '}';
    }
}

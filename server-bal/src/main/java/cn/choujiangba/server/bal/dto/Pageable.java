package cn.choujiangba.server.bal.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class Pageable<T> {
    private List<T> content = new LinkedList<>();
    private int currentPage;
    private boolean hasNextPage;
    private boolean hasPrePage;

    public List<T> getContent() {
        return content;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean getHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "content=" + content +
                ", currentPage=" + currentPage +
                ", hasNextPage=" + hasNextPage +
                ", hasPrePage=" + hasPrePage +
                '}';
    }
}

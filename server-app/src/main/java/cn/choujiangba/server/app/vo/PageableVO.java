package cn.choujiangba.server.app.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class PageableVO<T> {
    private List<T> content = new LinkedList<>();
    private int count;
    private int currentPage;
    private boolean hasNextPage;
    private boolean hasPrePage;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getContent() {
        return content;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }
}

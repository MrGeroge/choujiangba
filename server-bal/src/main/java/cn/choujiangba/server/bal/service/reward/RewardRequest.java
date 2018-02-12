package cn.choujiangba.server.bal.service.reward;

import java.util.Date;

/**
 *
 * 开奖请求
 *
 * Author:zhangyu
 * create on 15/11/5.
 */
public interface RewardRequest {

    /**
     * 时候能开奖
     * */
    boolean canOpen(Date now);

    /**
     * 执行具体开奖操作
     *
     * */
    void execute();

}

package cn.choujiangba.server.bal.service.reward;

import cn.choujiangba.server.dal.api.ActivityCompleteDao;
import cn.choujiangba.server.dal.po.ActivityComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 开奖队列
 *
 * Author:zhangyu
 * create on 15/11/5.
 */
public class RewardQuene {

    private final static Logger logger= LoggerFactory.getLogger(RewardQuene.class);

    private AtomicBoolean inited=new AtomicBoolean(false);

    private List<RewardRequest> rewardRequests=new CopyOnWriteArrayList<>();

    private TaskExecutor taskExecutor;

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    private RewardRequestFactory rewardRequestFactory;

    private ActivityCompleteDao activityCompleteDao;

    public void setRewardRequestFactory(RewardRequestFactory rewardRequestFactory) {
        this.rewardRequestFactory = rewardRequestFactory;
    }

    public void setActivityCompleteDao(ActivityCompleteDao activityCompleteDao) {
        this.activityCompleteDao = activityCompleteDao;
    }

    public void init(){
        if(!inited.compareAndSet(false,true))
            return;

        //恢复之前为开奖的
        for(ActivityComplete activityComplete:activityCompleteDao.findAll()){
            enqueue(rewardRequestFactory.build(activityComplete.getFinishTime(),activityComplete.getActivityId()));
        }

        logger.info("init reward quene");
        //启动线程用于循环检测时间
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Date now=new Date();

                    logger.debug(String.format("reward request quene size=%d",rewardRequests.size()));

                    for(RewardRequest req:rewardRequests){
                        if(req.canOpen(now)){
                            req.execute();
                            rewardRequests.remove(req);
                        }
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error("reward time check thread interrupted",e);
                    }
                }
            }
        });
    }

    /**
     * 添加开奖请求
     * */
    public void enqueue(RewardRequest req){
        rewardRequests.add(req);
    }

}

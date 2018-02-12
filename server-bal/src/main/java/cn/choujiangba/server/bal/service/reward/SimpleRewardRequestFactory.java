package cn.choujiangba.server.bal.service.reward;

import cn.choujiangba.server.dal.api.ActivityCompleteDao;
import cn.choujiangba.server.dal.api.ActivityDao;
import cn.choujiangba.server.dal.api.ActivityJoinRecordDao;
import cn.choujiangba.server.dal.api.ActivityResultDao;
import cn.choujiangba.server.dal.po.Activity;
import cn.choujiangba.server.dal.po.ActivityComplete;
import cn.choujiangba.server.dal.po.ActivityJoinRecord;
import cn.choujiangba.server.dal.po.ActivityResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * 创建开奖请求，开奖算法为简单的取随机数
 *
 * Author:zhangyu
 * create on 15/11/6.
 */
public class SimpleRewardRequestFactory implements RewardRequestFactory{

    private final static Logger logger= LoggerFactory.getLogger(SimpleRewardRequestFactory.class);

    /**
     * dao组件
     * */
    private ActivityDao activityDao;
    private ActivityCompleteDao activityCompleteDao;
    private ActivityJoinRecordDao activityJoinRecordDao;
    private ActivityResultDao activityResultDao;

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public void setActivityCompleteDao(ActivityCompleteDao activityCompleteDao) {
        this.activityCompleteDao = activityCompleteDao;
    }

    public void setActivityJoinRecordDao(ActivityJoinRecordDao activityJoinRecordDao) {
        this.activityJoinRecordDao = activityJoinRecordDao;
    }

    public void setActivityResultDao(ActivityResultDao activityResultDao) {
        this.activityResultDao = activityResultDao;
    }

    public RewardRequest build(final Date openTime,final long activityId){

        logger.debug(String.format("build request date=%s,activity=%d",
                new SimpleDateFormat("yy/MM/dd HH/mm/ss").format(openTime), activityId));

        return new RewardRequest() {
            @Override
            public boolean canOpen(Date now) {

                Calendar nowCalendar=Calendar.getInstance();
                nowCalendar.setTime(now);

                Calendar openCalendar=Calendar.getInstance();
                openCalendar.setTime(openTime);

                if(nowCalendar.compareTo(openCalendar)>=0){
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            public void execute() {
                logger.info(String.format("open reward activityId=%d",activityId));

                Activity activityDetail=activityDao.findOne(activityId);

                if(activityDetail==null){
                    logger.error(String.format("open reward error,cause [activityId=%d] not found",activityId));
                    return;
                }

                Random random = new Random();
                int winnerIndex=random.nextInt((int)activityDetail.getPrice())+1;

                double sum=0;
                /*
                * 此处考虑一人买多份时，概率应该相应变大的场景
                * */
                for (ActivityJoinRecord joinRec: activityJoinRecordDao.findByActivityId(activityId)){
                    sum+=joinRec.getPrice();
                    if(sum>winnerIndex){
                        long winnerId=joinRec.getUid();

                        //保存相关信息
                        activityDetail.setWinnerId(winnerId);
                        activityDetail.setStatus(3);
                        activityDao.save(activityDetail);

                        //TODO: activityCompleteDao 根据acitvityId删除
                        activityCompleteDao.deleteByActivityId(activityId);

                        ActivityResult result = new ActivityResult();
                        result.setItemId(activityDetail.getItemId());
                        result.setPrice(joinRec.getPrice());
                        result.setRewardTime(openTime);
                        result.setActivityId(activityId);
                        result.setWinnerId(winnerId);
                        result.setDesc(String.format("第%d期活动,获奖者ID为%d",activityId,winnerId));
                        activityResultDao.save(result);

                        break;
                    }
                }
            }
        };
    }

}

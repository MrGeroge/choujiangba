package cn.choujiangba.server.bal.service.reward;

import java.util.Date;

/**
 * Author:zhangyu
 * create on 15/11/5.
 */
public interface RewardRequestFactory {

    RewardRequest build(final Date openTime,final long activityId);

}

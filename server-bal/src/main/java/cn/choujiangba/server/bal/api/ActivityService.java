package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;

import java.util.List;

/**
 * Created by shuiyu on 2015/10/25.
 */
public interface ActivityService {
    /**
     * 添加一个活动
     * @param itemId NOT NULL 活动对应商品id
     * @param price NOT NULL 活动对应商品的价格
     * @param rewardInterval NOT NULL 活动开奖时间
     * @throws BizException
     */
    boolean addActivity(long itemId,double price,long rewardInterval)throws BizException;

    /**
     * 获取winner
     * @param page NOT NULL
     * @param count NOT NULL
     * @return
     * @throws BizException
     */
    Pageable<WinnerDTO> listWinner(int page,int count)throws BizException;

    /**
     * 获取正在进行中的活动
     * @param page NOT NULL
     * @param count NOT NULL
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO> listUnderwayActivity(int page,int count)throws BizException;

    /**
     * 获取已经筹集完成的活动
     * @param page
     * @param count
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO> listCompletedActivity(int page,int count)throws BizException;


    /**
     * 参加活动
     * @param activityId NOT NULL
     * @param uid NOT NULL
     * @param price NOT NULL
     * @throws BizException
     */
    long joinActivity(long activityId,
                         long uid,
                         double price,
                         double point,
                         String city,
                         String district,
                         double longitude,
                         double latitude,
                         String ip)throws BizException;

    /**
     * 获取活动详情
     * @param activityId
     * @return
     * @throws BizException
     */
    ActivityDTO getActivity(long activityId)throws BizException;

    /**
     * 判断是否已经参加过活动
     * @param uid 用户id
     * @param activityId 活动id
     * @return
     * @throws BizException
     */
    ActivityJoinRecordDTO checkJoin(long uid,long activityId)throws BizException;

    /**
     * 根据itemId，查询最新的活动
     * @param itemId 商品id
     * @return
     * @throws BizException
     */
    long findByItemId(long itemId)throws BizException;

    /**
     * 通过activityId查询参与者
     * @param activityId
     * @return
     * @throws BizException
     */
    Pageable<JoinerDTO> listJoinerByActivityId(int page,int count,long activityId)throws BizException;

    /**
     * 更改活动状态，只能从0-1，其他的自动完成
     * @param activityId
     * @param status
     * @return
     * @throws BizException
     */
    boolean updateStatus(long activityId,ActivityDTO.Status status)throws BizException;

    /**
     * 列出所有活动
     * @param page 页
     * @param count 每页多少个
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO> listAllActivity(int page,int count)throws BizException;

    /**
     *获取我参加的正在进行中的活动
     * @param uid NOT NULL
     * @param page 页
     * @param count 每页多少
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO>listMyUnderwayActivity(long uid,int page,int count )throws BizException;

    /**
     * 获取我参加的正在开奖的活动
     * @param uid NOT NULL
     * @param page 页
     * @param count 每页多少
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO>listMyCompleteActivity(long uid,int page,int count)throws BizException;

    /**
     *获取我参加的已经结束的活动
     * @param uid NOT NULL
     * @param page 页
     * @param count 每页多少
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO>listMyFinisheddActivity(long uid,int page,int count)throws BizException;

    /**
     * 获取我参加的所有活动记录
     * @param uid
     * @param page
     * @param count
     * @return
     * @throws BizException
     */
    Pageable<ActivityDTO>listMyActivityRecord(long uid,int page,int count)throws BizException;

    boolean addDelivery(long activityId, String expressName)throws BizException;

    boolean updateDelivery(long deliveryId)throws BizException;

}

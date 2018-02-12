package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.ActivityService;
import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.service.reward.RewardQuene;
import cn.choujiangba.server.bal.service.reward.RewardRequestFactory;
import cn.choujiangba.server.dal.api.*;
import cn.choujiangba.server.dal.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by shuiyu on 2015/10/25.
 */
public class ActivityServiceImpl implements ActivityService {

    private static Logger logger= LoggerFactory.getLogger(ActivityServiceImpl.class);
    private ActivityDao activityDao;
    private ItemDao itemDao;
    private ActivityCompleteDao activityCompleteDao;
    private ActivityResultDao activityResultDao;
    private ActivityUnderwayDao activityUnderwayDao;
    private ActivityJoinRecordDao activityJoinRecordDao;
    private AccountProfileDao accountProfileDao;
    private ItemBaseStatisticDao itemBaseStatisticDao;
    private ItemPurchaseStatisticDao itemPurchaseStatisticDao;
    private TaskExecutor taskExecutor;
    private ActivityDeliveryDao activityDeliveryDao;
    private TransactionDao transactionDao;
    private final double SCALE=10.0;
    @Autowired
    public void setActivityDeliveryDao(ActivityDeliveryDao activityDeliveryDao) {
        this.activityDeliveryDao = activityDeliveryDao;
    }



    @Autowired
    private RewardQuene rewardQuene;

    @Autowired
    private RewardRequestFactory rewardRequestFactory;


    @Autowired
    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Autowired
    public void setItemPurchaseStatisticDao(ItemPurchaseStatisticDao itemPurchaseStatisticDao) {
        this.itemPurchaseStatisticDao = itemPurchaseStatisticDao;
    }

    @Autowired
    public void setItemBaseStatisticDao(ItemBaseStatisticDao itemBaseStatisticDao) {
        this.itemBaseStatisticDao = itemBaseStatisticDao;
    }

    @Autowired
    public void setAccountProfileDao(AccountProfileDao accountProfileDao) {
        this.accountProfileDao = accountProfileDao;
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }
    @Autowired
    public void setActivityCompleteDao(ActivityCompleteDao activityCompleteDao) {
        this.activityCompleteDao = activityCompleteDao;
    }
    @Autowired
    public void setActivityResultDao(ActivityResultDao activityResultDao) {
        this.activityResultDao = activityResultDao;
    }
    @Autowired
    public void setActivityUnderwayDao(ActivityUnderwayDao activityUnderwayDao) {
        this.activityUnderwayDao = activityUnderwayDao;
    }
    @Autowired
    public void setActivityJoinRecordDao(ActivityJoinRecordDao activityJoinRecordDao) {
        this.activityJoinRecordDao = activityJoinRecordDao;
    }

    @Override
    public boolean addActivity(long itemId, double price, long rewardInterval) throws BizException {
        if(itemId<1||price<1||rewardInterval<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"parameters are  not valid");
        logger.info(String.format("add activity itemId=%d,price=%f,rewardInterval=%d", itemId, price, rewardInterval));

        Activity activity = new Activity();
        activity.setItemId(itemId);
        activity.setPrice(price);
        activity.setRewardInterval(rewardInterval);
        activity.setStatus(1);

        try {
            activityDao.save(activity);
            ActivityUnderway underway = new ActivityUnderway();
            underway.setActivityId(activity.getId());
            underway.setItemId(itemId);
            underway.setPrice(price);
            underway.setRealPrice(0);
            activityUnderwayDao.save(underway);
            itemBaseStatisticDao.updatePublishedNum(itemId);
            logger.info("successfully add activity");
        }catch (Exception e){
            logger.info("failed to add activity"+e.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public Pageable<WinnerDTO> listWinner(int page, int count) throws BizException {
       if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ActivityResult> items =activityResultDao.findAll(new PageRequest(page-1,count,sort));

        Pageable<WinnerDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityResult item:items){
            WinnerDTO winnerDTO = new WinnerDTO();
            AccountProfile profile = accountProfileDao.findByUid(item.getWinnerId());
            winnerDTO.setUid(item.getWinnerId());
            winnerDTO.setTime(item.getRewardTime());
            winnerDTO.setPrice(item.getPrice());
            winnerDTO.setNickname(profile.getNickname());
            winnerDTO.setDes(item.getDesc());
            result.getContent().add(winnerDTO);
        }
        logger.info("successfully list winner " + result.toString());

        return result;

    }

    @Override
    public Pageable<ActivityDTO> listUnderwayActivity(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ActivityUnderway> items  = activityUnderwayDao.findAll(new PageRequest(page-1,count,sort));
        Pageable<ActivityDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityUnderway item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setPrice(item.getPrice());
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setItemThumbnailUrl(good.getThumbnail_url());
            activityDTO.setStatus(ActivityDTO.Status.IN_PROGRESS);
            activityDTO.setRealPrice(item.getRealPrice());
            activityDTO.setPercent(item.getRealPrice()* 100 / item.getPrice() );
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get underway activity "+result.toString());
        return result;
    }

    @Override
    public Pageable<ActivityDTO> listCompletedActivity(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ActivityComplete> items  = activityCompleteDao.findAll(new PageRequest(page-1,count,sort));
        Pageable<ActivityDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityComplete item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setPrice(item.getPrice());
            activityDTO.setItemThumbnailUrl(good.getThumbnail_url());
            activityDTO.setStatus(ActivityDTO.Status.WILL_ANNOUNCE);
            activityDTO.setRealPrice(item.getPrice());
            activityDTO.setPercent(100);
            activityDTO.setRewardInterval(item.getFinishTime().getTime() - new Date().getTime());
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get completed activity "+result.toString());
        return result;
    }

    @Override
    public long joinActivity(final long activityId, long uid, double price,double point,String city,String district,double longitude,double latitude,String ip) throws BizException {
        if(activityId<1||uid<1||price<0||point<0)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        final Activity activity = activityDao.findOne(activityId);

        if(activity==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"there is no such activity");
        switch (activity.getStatus()){
            case 0:throw new BizException(BizException.ERROR_CODE_ACTIVITY_NOT_BEGIN,String.format("activityId=%d is not statt",activityId));
            case 1:
                break;
            default:throw new BizException(BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR,String.format("activityId=%d status not correct now is %d",activityId,activity.getStatus()));
        }

        final ActivityUnderway underway = activityUnderwayDao.findByActivityId(activityId);
        AccountProfile profile = accountProfileDao.findByUid(uid);

        double buyPrice = point+price*SCALE;
        double diff = underway.getPrice()-underway.getRealPrice()-buyPrice;
        double rest = profile.getBalance()-point;
        if(diff<0)
            throw new BizException(BizException.ERROR_CODE_ACTIVITY_BALANCE_FULL,String.format("activityId=%d now balance=%f but buy price =%f overflow totalNeed=%f",activityId,underway.getRealPrice(),price,underway.getPrice()));
        if(rest<0)
            throw new BizException(BizException.ERROR_CODE_BALANCE_NOT_ENOUGH,String.format("userId=%d 's balance is not enough now is %f but want to pay price=%f",uid,profile.getBalance(),price));

        accountProfileDao.testUpdateBalance(uid,0-point);

        Transaction trans = new Transaction();
        trans.setUid(uid);
        trans.setCreate_at(new Date());
        trans.setCoin_num(0 - buyPrice);
        trans.setDesc("参加第"+activityId+"期活动，花费"+String.valueOf(price)+"金币 "+String.valueOf(point)+"积分");
        transactionDao.save(trans);
        logger.info("successfully add transaction"+trans.toString());

        ActivityJoinRecord joinRecord = new ActivityJoinRecord();
        joinRecord.setItemId(activity.getItemId());
        joinRecord.setPrice(buyPrice);
        joinRecord.setUid(uid);
        joinRecord.setCity(city);
        joinRecord.setJoinTime(new Date());
        joinRecord.setDistrict(district);
        joinRecord.setIp(ip);
        joinRecord.setActivityId(activityId);
        activityJoinRecordDao.save(joinRecord);
        logger.info("successfully add join record"+joinRecord.toString());

        ItemPurchaseStatistic purchaseStatistic = new ItemPurchaseStatistic();
        purchaseStatistic.setBuy_time(new Date());
        purchaseStatistic.setItem_id(activity.getItemId());
        purchaseStatistic.setPay(buyPrice);
        purchaseStatistic.setUid(uid);
        itemPurchaseStatisticDao.save(purchaseStatistic);
        logger.info("successfully add purchase statistic"+purchaseStatistic.toString());

        if(diff>0) {
            activityUnderwayDao.updateRealPrice(underway.getId(), buyPrice);
        }
        if(diff==0){
            activityUnderwayDao.delete(underway.getId());
            logger.info("successfully delete underway activity" + underway.toString());
            final ActivityComplete complete = new ActivityComplete();
            complete.setItemId(activity.getItemId());
            complete.setActivityId(activityId);
            complete.setFinishTime(new Date(new Date().getTime() + activity.getRewardInterval()));
            complete.setPrice(activity.getPrice());
            activityCompleteDao.save(complete);
            logger.info("successfully add activity complete " + complete.toString());

            activity.setWinnerId(0);
            activity.setStatus(2);
            activityDao.save(activity);

            //放入开奖队列
            rewardQuene.init();
            rewardQuene.enqueue(rewardRequestFactory.build(complete.getFinishTime(),activityId));
        }
        return joinRecord.getId();
    }

    @Override
    public ActivityDTO getActivity(long activityId) throws BizException {
        if(activityId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        logger.info("get activity=%d",activityId);

        Activity activity = activityDao.findOne(activityId);

        logger.info("successfully get activity  "+activity.toString());

        ActivityDTO activityDTO = new ActivityDTO();
        Item item = itemDao.findOne(activity.getItemId());
        ItemDetailDTO detailDTO = new ItemDetailDTO();
        detailDTO.setItemId(item.getId());
        detailDTO.setName(item.getName());
        detailDTO.setDetail(item.getDetail());
        detailDTO.setThumbnailUrl(item.getThumbnail_url());
        detailDTO.setPrice(item.getPrice());
        if(item.getDesc_img_urls()!=null)
            for(String url:item.getDesc_img_urls().split(";"))
                detailDTO.getDescImgUrl().add(url);

        activityDTO.setItemDetail(detailDTO);
        activityDTO.setPrice(activity.getPrice());
        activityDTO.setActivityId(activityId);
        activityDTO.setScale(SCALE);

        switch (activity.getStatus()){
            case (1):
                ActivityUnderway underway = activityUnderwayDao.findByActivityId(activityId);
                activityDTO.setRealPrice(underway.getRealPrice());
                activityDTO.setPercent(underway.getRealPrice()* 100 / underway.getPrice() );
                activityDTO.setStatus(ActivityDTO.Status.IN_PROGRESS);
                break;
            case (2):
                ActivityComplete complete = activityCompleteDao.findByActivityId(activityId);
                activityDTO.setRewardInterval(complete.getFinishTime().getTime()-new Date().getTime());
                activityDTO.setStatus(ActivityDTO.Status.WILL_ANNOUNCE);
                break;
            case(3):
                ActivityResult winner = activityResultDao.findByActivityId(activityId);
                AccountProfile profile = accountProfileDao.findByUid(winner.getWinnerId());
                WinnerDTO winnerDTO = new WinnerDTO();
                ActivityJoinRecord record = activityJoinRecordDao.findByUidAndActivityId(winner.getWinnerId(), winner.getActivityId());
                winnerDTO.setPrice(winner.getPrice());
                winnerDTO.setTime(winner.getRewardTime());
                winnerDTO.setUid(winner.getWinnerId());
                winnerDTO.setPrice(winner.getPrice());
                winnerDTO.setNickname(profile.getNickname());
                winnerDTO.setIp(record.getIp());
                activityDTO.setWinner(winnerDTO);
                activityDTO.setStatus(ActivityDTO.Status.WILL_SEND);
                break;
            default:break;
        }
        logger.info("successfully get activity"+activityDTO.toString());
        return activityDTO;
    }

    @Override
    public ActivityJoinRecordDTO checkJoin(long uid, long activityId) throws BizException {
       if(uid<1||activityId<1)
           throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        ActivityJoinRecord activityJoinRecord = activityJoinRecordDao.findByUidAndActivityId(uid, activityId);
        logger.info(String.format("check uid=%d joined activityId=%d", uid, activityId));
        ActivityJoinRecordDTO record = new ActivityJoinRecordDTO();
        if(activityJoinRecord==null) {
            record.setJoined(false);
            return record;
        }
        record.setJoined(true);
        record.setUid(activityJoinRecord.getUid());
        record.setJoinId(activityJoinRecord.getId());
        record.setJoinPrice(activityJoinRecord.getPrice());
        logger.info("get join record"+record.toString());
        return record;
    }

    @Override
    public long findByItemId(long itemId) throws BizException {
        if(itemId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        Page<Activity> activity = activityDao.findByItemId(itemId, new PageRequest(1, 1, new Sort(Sort.Direction.DESC, "id")));
        if(activity==null)
            return 0;
        return activity.getContent().get(0).getId();
    }

    @Override
    public Pageable<JoinerDTO> listJoinerByActivityId(int page,int count,long activityId) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        logger.info(String.format("list joiner to activityId=%d",activityId));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ActivityJoinRecord> items  = activityJoinRecordDao.findByActivityId(activityId, new PageRequest(page - 1, count, sort));
        Pageable<JoinerDTO> result = new Pageable<>();
        logger.info("successfully list joiners to activityId=%d",activityId);
        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityJoinRecord item:items){
            JoinerDTO joinerDTO = new JoinerDTO();
            AccountProfile profile = accountProfileDao.findByUid(item.getUid());
            joinerDTO.setDate(item.getJoinTime());
            joinerDTO.setNickname(profile.getNickname());
            joinerDTO.setPrice(item.getPrice());
            joinerDTO.setIp(item.getIp());
            joinerDTO.setUid(item.getUid());
            joinerDTO.setUrl(profile.getAvatar_url());
            result.getContent().add(joinerDTO);
        }
        logger.info("successfully package joiner to activityId =  " + activityId + result.toString());
        return result;
    }

    @Override
    public boolean updateStatus(long activityId, ActivityDTO.Status status) throws BizException {
       if(activityId<1)
           throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        logger.info(String.format("update activityId=%d activity status to ", activityId, status.getNum()));
        Activity activity = activityDao.findOne(activityId);

        if(activity==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"there id not such activity");
        logger.info("successfully get activity" + activity.toString());
        switch (activity.getStatus()){
            case 0:
                if(status.getNum()!=1)
                    throw new BizException(BizException.ERROR_CODE_STATUS_WRONG_INPUT,"cannot change status to "+status.getNum());
                ActivityUnderway underway = new ActivityUnderway();
                underway.setActivityId(activity.getId());
                underway.setItemId(activity.getItemId());
                underway.setPrice(activity.getPrice());
                underway.setRealPrice(0);
                activityUnderwayDao.save(underway);
                logger.info("successfully change status to 1"+underway.toString());
                break;
            case 1:
                switch (status.getNum()){
                    case 1:
                        break;
                    case 0:
                        activityUnderwayDao.deleteByActivityId(activityId);
                        break;
                    default:throw new BizException(BizException.ERROR_CODE_STATUS_WRONG_INPUT,"cannot change status to"+status.getNum());
                }
                break;
            //这里并不提供status为2和3时候的状态更改，
            default:throw new BizException(BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR,"activity status error cannot change status");

        }
        activity.setStatus(status.getNum());
        try {
            activityDao.save(activity);
            logger.info("successfully update status"+activity.toString());
            return true;
        }catch (Exception e){
            logger.info("Failed to update status"+status.getNum());
            return false;
        }
    }

    @Override
    public Pageable<ActivityDTO> listAllActivity(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<Activity> items  = activityDao.findAll(new PageRequest(page - 1, count, sort));
        Pageable<ActivityDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(Activity item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            activityDTO.setActivityId(item.getId());
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setName(good.getName());
            detailDTO.setItemId(good.getId());

            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            activityDTO.setActivityId(item.getId());
            activityDTO.setItemDetail(detailDTO);
            if(item.getStatus()==0)
                activityDTO.setStatus(ActivityDTO.Status.NOT_START);
            if(item.getStatus()==1)
                activityDTO.setStatus(ActivityDTO.Status.IN_PROGRESS);
            if(item.getStatus()==2)
                activityDTO.setStatus(ActivityDTO.Status.WILL_ANNOUNCE);
            if(item.getStatus()==3)
                activityDTO.setStatus(ActivityDTO.Status.WILL_SEND);
            activityDTO.setPrice(item.getPrice());
            result.getContent().add(activityDTO);
        }
        logger.info("successfully list all activity  " + result.toString());
        return result;
    }

    @Override
    public Pageable<ActivityDTO> listMyUnderwayActivity(long uid, int page, int count) throws BizException {
        if(uid<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable<ActivityDTO> result = new Pageable<>();
        Page<ActivityJoinRecord> items = activityJoinRecordDao.findByUid(uid,new PageRequest(page - 1, count, sort));

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityJoinRecord item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            Activity activity = activityDao.findByIdAndStatus(item.getActivityId(),1);
            if(activity == null)
                continue;
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setPrice(activity.getPrice());
            activityDTO.setWinnerId(activity.getWinnerId());
            activityDTO.setStatus(ActivityDTO.Status.IN_PROGRESS);
            ActivityUnderway underway;
            try {
                underway = activityUnderwayDao.findByActivityId(activity.getId());
            }catch(Exception e){
                logger.error(String.format("activityId=%d is not a underway activity it's status is %d",activity.getId(),activity.getStatus()));
                throw new BizException(BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR,"error status");
            }
            activityDTO.setRealPrice(underway.getRealPrice());
            activityDTO.setPercent(underway.getRealPrice() * 100 / underway.getPrice());
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get my activity record" + result.toString());
        return result;
    }

    @Override
    public Pageable<ActivityDTO> listMyCompleteActivity(long uid, int page, int count) throws BizException {
        if(uid<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable<ActivityDTO> result = new Pageable<>();
        Page<ActivityJoinRecord> items = activityJoinRecordDao.findByUid(uid,new PageRequest(page - 1, count, sort));

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityJoinRecord item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            Activity activity = activityDao.findByIdAndStatus(item.getActivityId(),2);
            if(activity == null){
                continue;
            }
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setPrice(activity.getPrice());
            activityDTO.setWinnerId(activity.getWinnerId());
            activityDTO.setStatus(ActivityDTO.Status.WILL_ANNOUNCE);
            activityDTO.setPercent(100);
            activityDTO.setRealPrice(activity.getPrice());
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get my activity record" + result.toString());
        return result;
    }

    @Override
    public Pageable<ActivityDTO> listMyFinisheddActivity(long uid, int page, int count) throws BizException {
        if(uid<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable<ActivityDTO> result = new Pageable<>();
        Page<ActivityJoinRecord> items = activityJoinRecordDao.findByUid(uid,new PageRequest(page - 1, count, sort));

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityJoinRecord item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            Activity activity = activityDao.findByIdAndStatus(item.getActivityId(), 3);
            if(activity == null){
                continue;
            }
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setPrice(activity.getPrice());
            activityDTO.setWinnerId(activity.getWinnerId());
            activityDTO.setStatus(ActivityDTO.Status.WILL_SEND);
            activityDTO.setPercent(100);
            activityDTO.setRealPrice(activity.getPrice());
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get my activity record" + result.toString());
        return result;
    }

    @Override
    public Pageable<ActivityDTO> listMyActivityRecord(long uid, int page, int count) throws BizException {
        if(uid<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable<ActivityDTO> result = new Pageable<>();
        Page<ActivityJoinRecord> items = activityJoinRecordDao.findByUid(uid,new PageRequest(page - 1, count, sort));

        result.setCurrentPage(page);
        result.setHasNextPage(items.hasNext());
        result.setHasPrePage(items.hasPrevious());

        for(ActivityJoinRecord item:items){
            ActivityDTO activityDTO = new ActivityDTO();
            Item good = itemDao.findOne(item.getItemId());
            Activity activity = activityDao.findOne(item.getActivityId());
            ItemDetailDTO detailDTO = new ItemDetailDTO();
            detailDTO.setItemId(good.getId());
            detailDTO.setPrice(good.getPrice());
            detailDTO.setThumbnailUrl(good.getThumbnail_url());
            detailDTO.setDetail(good.getDetail());
            detailDTO.setName(good.getName());
            activityDTO.setActivityId(item.getActivityId());
            activityDTO.setItemDetail(detailDTO);
            activityDTO.setPrice(activity.getPrice());
            activityDTO.setWinnerId(activity.getWinnerId());
            switch (activity.getStatus()){
                case 0:
                    activityDTO.setStatus(ActivityDTO.Status.NOT_START);
                    activityDTO.setPercent(0.0);
                    activityDTO.setRealPrice(0.0);
                    break;
                case 1:
                    activityDTO.setStatus(ActivityDTO.Status.IN_PROGRESS);
                    ActivityUnderway underway = activityUnderwayDao.findByActivityId(item.getActivityId());
                    if(underway==null)
                        throw new BizException(BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR,"status error");
                    activityDTO.setPercent(underway.getRealPrice()*100/activity.getPrice());
                    activityDTO.setRealPrice(underway.getRealPrice());
                    break;
                case 2:
                    activityDTO.setStatus(ActivityDTO.Status.WILL_ANNOUNCE);
                    activityDTO.setPercent(100);
                    activityDTO.setRealPrice(activity.getPrice());
                    break;
                case 3:
                    activityDTO.setStatus(ActivityDTO.Status.WILL_SEND);
                    activityDTO.setPercent(100);
                    activityDTO.setRealPrice(activity.getPrice());
                    break;
                default:
                    activityDTO.setStatus(ActivityDTO.Status.NOT_START);
            }
            result.getContent().add(activityDTO);
        }
        logger.info("successfully get my activity record" + result.toString());
        return result;
    }

    @Override
    public boolean addDelivery(long activityId, String expressName) throws BizException {
        if(activityId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"activityID is not valid");
        if(StringUtils.isEmpty(expressName))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"expressName is null");

        Activity activity = activityDao.findOne(activityId);

        if(activity==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"there is no such data mapping activityId="+activityId);
        if(activity.getWinnerId()==0)
            throw new BizException(BizException.ERROR_CODE_ACTIVITY_STATUS_ERROR,"activity status is not correct");

        ActivityDelivery activityDelivery = new ActivityDelivery();
        activityDelivery.setItemId(activity.getItemId());
        activityDelivery.setActivityId(activityId);
        activityDelivery.setCreateAt(new Date());
        activityDelivery.setExpressNum(expressName);
        activityDelivery.setReceived(false);
        try {
            activityDeliveryDao.save(activityDelivery);
            return true;
        }catch (Exception e)
        {
            logger.error("add Delivery error",e);
        }
        return false;
    }

    @Override
    public boolean updateDelivery(long deliveryId) throws BizException {
        return false;
    }

}

package cn.choujiangba.server.app.controller;

import cn.choujiangba.server.app.vo.*;
import cn.choujiangba.server.bal.api.ActivityService;
import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.dal.po.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hao on 2015/10/25.
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    private final static Logger logger= LoggerFactory.getLogger(ActivityController.class);

    private SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private ItemService itemService;
    private ActivityService activityService;
    private TokenService tokenService;

    @Autowired
    public ActivityController setItemService(ItemService itemService){
        this.itemService = itemService;
        return this;
    }

    @Autowired
    public ActivityController setActivityService(ActivityService activityService){
        this.activityService = activityService;
        return this;
    }

    @Autowired
    public ActivityController setTokenService(TokenService tokenService){
        this.tokenService = tokenService;
        return this;
    }

    /**
     * 获取APP首页相关数据
     * @param last_time
     * @return
     *      {
                hot_activities:[
                    {
                        activity_id:"",
                        banner_url:""
                    }
                ],
                complete_activities:[
                    {
                        activity_id:"",
                        item_name:"商品名称",
                        item_thumbnail_url:"商品缩略图",
                        activity_finish_interval:"距离开奖时长"
                    }
                ],
                underway_activities:[
                    {
                        activity_id:"",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格",
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比"
                    }
                ],
                winners:[
                    {
                        nickname:"",
                        time:"距离1分钟",
                        desc:"一部iphone6s"
                    }
                ]
            }
     * @throws BizException
     */
    @RequestMapping(value = "/home",method = RequestMethod.POST)
    public Map<String,Object> home(
            @RequestParam(value = "last_time",required = false)String last_time)
            throws BizException{
        //获取热门商品banner
        List<HotItemDTO> hotList = itemService.listAllHot();

        List<HotActivityVO> hotResult = new LinkedList<>();
        HotActivityVO hotActivityVO = null;
        for(HotItemDTO hotItemDTO : hotList){
            hotActivityVO = new HotActivityVO();
            //根据item_id查找最新活动的Activity_id
            hotActivityVO.setActivity_id(
                    activityService.findByItemId(hotItemDTO.getItemId()));

            hotActivityVO.setBanner_url(hotItemDTO.getBanner());

            hotResult.add(hotActivityVO);

            logger.info(hotActivityVO.toString());
        }


        //获取已完成的活动，数目为2
        Pageable<ActivityDTO> completePageable = activityService.listCompletedActivity(1,2);
        List<DoneActivityVO> completeResult = new LinkedList<>();
        DoneActivityVO doneActivityVO = null;
        for(ActivityDTO activityDTO : completePageable.getContent()){
            doneActivityVO = new DoneActivityVO();
            doneActivityVO.setActivity_id(activityDTO.getActivityId());
            doneActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            doneActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            doneActivityVO.setActivity_finish_interval(activityDTO.getRewardInterval() / 1000);

            completeResult.add(doneActivityVO);

            logger.info(doneActivityVO.toString());
        }

        //获取正在进行的活动，数目为2
        Pageable<ActivityDTO> underPageable = activityService.listUnderwayActivity(1,2);
        List<UnderwayActivityVO> underwayResult = new LinkedList<>();
        UnderwayActivityVO underwayActivityVO = null;
        for(ActivityDTO activityDTO : underPageable.getContent()){
            underwayActivityVO = new UnderwayActivityVO();
            underwayActivityVO.setActivity_id(activityDTO.getActivityId());
            underwayActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            underwayActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            underwayActivityVO.setActivity_real(activityDTO.getRealPrice());
            underwayActivityVO.setActivity_price(activityDTO.getPrice());
            underwayActivityVO.setActivity_percentage(activityDTO.getPercent());

            underwayResult.add(underwayActivityVO);

            logger.info(underwayActivityVO.toString());
        }

        //获取winners,数目为10
        Pageable<WinnerDTO> winnerPageable = activityService.listWinner(1,10);
        List<WinnerVO> winnerResult = new LinkedList<>();
        WinnerVO winnerVO = null;
        for(WinnerDTO winnerDTO : winnerPageable.getContent()){
            winnerVO = new WinnerVO();
            winnerVO.setNickname(winnerDTO.getNickname());

            long interval = ((new Date()).getTime() - winnerDTO.getTime().getTime())/1000;

            if(interval < 60){
                winnerVO.setTime("距离当前小于分钟");
            }else {
                winnerVO.setTime("距离当前" + interval / 60 + "分钟");
            }
            winnerVO.setDesc(winnerDTO.getDes());

            winnerResult.add(winnerVO);

            logger.info(winnerVO.toString());
        }

        Map<String,Object> result = new HashMap<>();
        result.put("hot_activities",hotResult);
        result.put("complete_activities",completeResult);
        result.put("underway_activities",underwayResult);
        result.put("winners",winnerResult);

        logger.info("home page success");

        return result;
    }

    /**
     * 参加活动（购买活动）
     * @param token token
     * @param activity_id 活动ID
     * @param choujiang_price 平台积分
     * @param duomeng_price 多盟积分
     * @param city 所在城市
     * @param district 所在区域
     * @param longitude 经纬度
     * @param latitude 经纬度
     * @return
     *      {
                result:"success/failed",
                message:"",
            }
     * @throws BizException
     */
    @RequestMapping(value = "/join",method = RequestMethod.POST)
    public Map<String,String> join(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "activity_id",required = true)long activity_id,
            @RequestParam(value = "choujiang_price",required = true)double choujiang_price,
            @RequestParam(value = "duomeng_price",required = true)double duomeng_price,
            @RequestParam(value = "city",required = false)String city,
            @RequestParam(value = "district",required = false)String district,
            @RequestParam(value = "longitude",required = false,defaultValue = "0")double longitude,
            @RequestParam(value = "latitude",required = false,defaultValue = "0")double latitude)
            throws BizException{
        long userId = tokenService.validate(token);

        Map<String,String> result = new HashMap<>();

        ActivityJoinRecordDTO activityJoinRecordDTO = activityService.checkJoin(userId, activity_id);

        if(activityJoinRecordDTO.isJoined()){
            result.put("result","failed");
            result.put("message","参加失败");
        }else {

            try {

                long record_id = activityService.joinActivity(activity_id, userId, duomeng_price, choujiang_price, city, district, longitude, latitude,
                        httpServletRequest.getRemoteAddr());

                result.put("result", "success");
                result.put("message", "参加成功");
                result.put("record_id", String.valueOf(record_id));
            }catch(BizException e){
                if(e.getErrorCode() == BizException.ERROR_CODE_BALANCE_NOT_ENOUGH){
                    result.put("result","failed");
                    result.put("message","金币不足");
                }else if(e.getErrorCode() == BizException.ERROR_CODE_ACTIVITY_BALANCE_FULL){
                    result.put("result","failed");
                    result.put("message","活动筹集已满");
                }else if(e.getErrorCode() == BizException.ERROR_CODE_ACTIVITY_NOT_BEGIN) {
                    result.put("result", "failed");
                    result.put("message", "活动未开始");
                }else{
                    throw e;
                }
            }
        }

        return result;
    }

    /**
     * 获取商品详情
     * @param token token
     * @param activity_id 活动ID
     * @return
     *      {
                activity_id:"",
                activity_status:"当前活动的状态,1 进行中 2 等待开奖中 3 已开奖",
                activity_price:"活动中商品金币总价格",
                activity_ext:{
                    #有token#
                        joined:"当前用户是否已经参加"
                        join_price:"当joined=true,参与价格"
                        join_id:"当joined=true,参与编号"
                    #当status=1#
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比"
                    #当status=2#
                        reward_interval:"距离开奖时间"
                    #当status=3#
                        winner:{
                            account_id:"",
                            nickname:"",
                            ip:"",
                            timestamp:"参与时间"，
                            price:"参与价格"
                        }
                },
                item_info:{
                    name:"商品名称",
                    price:"商品实际价格"
                    detail:"商品介绍详情"
                    img_urls:[
                        "",""
                    ]
                },
                joiners:{
                    "content":[
                        {
                            account_id:"",
                            avatar_url:"",
                            nickname:"",
                            ip:"",
                            timestamp:"参与时间"，
                            price:"参与价格"
                        }
                    ]
                    "currentPage":1,
                    "count":10,
                    "hasNextPage":true,
                    "hasPrePage":true
                }
            }
     * @throws BizException
     */
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public Map<String,Object> detail(
            @RequestParam(value = "token",required = false)String token,
            @RequestParam(value = "activity_id",required = true)long activity_id)
            throws BizException{

        /**
         * 判断是否有token
         */
        long userId = 0;

        if(!(token == null || "".equals(token))){
            userId = tokenService.validate(token);
        }

        /**
         * 在result中加入activity_id、activity_status、activity_price键对值
         */
        ActivityDTO activityDTO = activityService.getActivity(activity_id);

        Map<String,Object> result = new HashMap<>();
        result.put("activity_id",activityDTO.getActivityId());
        result.put("activity_status",activityDTO.getStatus().getNum());
        result.put("activity_price",activityDTO.getPrice());
        result.put("duomeng_proportion",activityDTO.getScale());

        /**
         * 根据token是否存在以及活动状态进行activity_ext键对值的添加
         */
        //activity活动的扩展字段
        Map<String,Object> activity_ext = new HashMap<>();
        if(userId != 0){
            //判断用户是否参加了活动
            ActivityJoinRecordDTO activityJoinRecordDTO =
                    activityService.checkJoin(userId,activity_id);

            //向扩展字段中添加是否参加活动的标识
            activity_ext.put("joined",activityJoinRecordDTO.isJoined());

            //若用户参加了活动，则向扩展字段中加join_price和join_id
            if(activityJoinRecordDTO.isJoined()){
                activity_ext.put("join_price",activityJoinRecordDTO.getJoinPrice());
                activity_ext.put("join_id",activityJoinRecordDTO.getJoinId());
            }
        }

        //根据活动状态在扩展字段中添加
        switch (activityDTO.getStatus().getNum()){
            case 1:
                activity_ext.put("activity_real",activityDTO.getRealPrice());
                activity_ext.put("activity_percentage",activityDTO.getPercent());
                break;
            case 2:
                activity_ext.put("reward_interval",activityDTO.getRewardInterval() / 1000);
                break;
            case 3:
                WinnerDetailVO winnerDetailVO = new WinnerDetailVO();

                winnerDetailVO.setAccount_id(activityDTO.getWinner().getUid());
                winnerDetailVO.setNickname(activityDTO.getWinner().getNickname());
                winnerDetailVO.setTimestamp(simple.format(activityDTO.getWinner().getTime()));
                winnerDetailVO.setPrice(activityDTO.getWinner().getPrice());
                winnerDetailVO.setIp(activityDTO.getWinner().getIp());

                activity_ext.put("winner",winnerDetailVO);
                break;
            default:
                break;
        }

        result.put("activity_ext", activity_ext);


        /**
         * item_info键对值的添加
         */
        ItemDetailVO itemDetailVO = new ItemDetailVO();
        itemDetailVO.setId(activityDTO.getItemDetail().getItemId());
        itemDetailVO.setName(activityDTO.getItemDetail().getName());
        itemDetailVO.setDetail(activityDTO.getItemDetail().getDetail());
        itemDetailVO.setPrice(activityDTO.getItemDetail().getPrice());
        for(String url : activityDTO.getItemDetail().getDescImgUrl()){
            itemDetailVO.getImg_urls().add(url);
        }
        result.put("item_info",itemDetailVO);


        /**
         * joiners键对值的添加
         */
        Pageable<JoinerDTO> pageable =
                activityService.listJoinerByActivityId(1,10,activity_id);

        PageableVO<JoinerVO> joinerResult = new PageableVO<>();



        JoinerVO joinerVO = null;
        for(JoinerDTO joinerDTO : pageable.getContent()){
            joinerVO = new JoinerVO();
            joinerVO.setAccount_id(joinerDTO.getUid());
            joinerVO.setNickname(joinerDTO.getNickname());
            joinerVO.setAvatar_url(joinerDTO.getUrl());
            joinerVO.setPrice(joinerDTO.getPrice());
            joinerVO.setIp(joinerDTO.getIp());
            joinerVO.setTimestamp(simple.format(joinerDTO.getDate()));

            joinerResult.getContent().add(joinerVO);
        }

        joinerResult.setCount(pageable.getContent().size());
        joinerResult.setCurrentPage(pageable.getCurrentPage());
        joinerResult.setHasPrePage(pageable.getHasPrePage());
        joinerResult.setHasNextPage(pageable.getHasNextPage());

        result.put("joiners", joinerResult);

        logger.info(String.format("get detail success activity_id=%d",activity_id));

        return result;
    }

    /**
     * 获取进行中的活动列表
     * @param page 页号
     * @param count 页面数量
     * @return
     *      {
                "content":[
                    {
                        activity_id:"",
                        activity_status:"当前活动的状态,1 进行中 2 等待开奖中 3 已开奖",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格",
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比"
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/underway/list",method = RequestMethod.POST)
    public PageableVO<UnderwayActivityVO> listUnderway(
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        Pageable<ActivityDTO> pageable = activityService.listUnderwayActivity(page,count);

        PageableVO<UnderwayActivityVO> result = new PageableVO<>();

        UnderwayActivityVO underwayActivityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            underwayActivityVO = new UnderwayActivityVO();
            underwayActivityVO.setActivity_id(activityDTO.getActivityId());
            underwayActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            underwayActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            underwayActivityVO.setActivity_status(activityDTO.getStatus().getNum());
            underwayActivityVO.setActivity_price(activityDTO.getPrice());
            underwayActivityVO.setActivity_real(activityDTO.getRealPrice());
            underwayActivityVO.setActivity_percentage(activityDTO.getPercent());

            result.getContent().add(underwayActivityVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info("list underwayActivities success");

        return result;
    }

    /**
     * 获取完成筹集,即将抽奖的活动列表
     * @param page 页号
     * @param count 页面数量
     * @return
     *      {
                "content":[
                    {
                        activity_id:"",
                        activity_status:"当前活动的状态,2 等待开奖中",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格"
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/complete/list",method = RequestMethod.POST)
    public PageableVO<ActivityVO> listComplete(
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        Pageable<ActivityDTO> pageable = activityService.listCompletedActivity(page, count);

        PageableVO<ActivityVO> result = new PageableVO<>();

        ActivityVO activityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            activityVO = new ActivityVO();
            activityVO.setActivity_id(activityDTO.getActivityId());
            activityVO.setActivity_status(activityDTO.getStatus().getNum());
            activityVO.setActivity_price(activityDTO.getPrice());
            activityVO.setItem_name(activityDTO.getItemDetail().getName());
            activityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            activityVO.setActivity_finish_interval(activityDTO.getRewardInterval()/1000);

            result.getContent().add(activityVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info("list completeActivities success");

        return result;
    }

    /**
     * 获取参加该活动的用户列表
     * @param activity_id 活动ID
     * @param page 页号
     * @param count 页面数量
     * @return
     *      {
                "content":[
                    {
                        account_id:"",
                        avatar_url:"",
                        nickname:"",
                        ip:"",
                        timestamp:"参与时间"，
                        price:"参与价格"
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/involved/list",method = RequestMethod.POST)
    public PageableVO<JoinerVO> listInvolved(
            @RequestParam(value = "activity_id",required = true)long activity_id,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        Pageable<JoinerDTO> pageable = activityService.listJoinerByActivityId(page, count, activity_id);

        PageableVO<JoinerVO> result = new PageableVO<>();

        JoinerVO joinerVO = null;
        for(JoinerDTO joinerDTO : pageable.getContent()){
            joinerVO = new JoinerVO();
            joinerVO.setAccount_id(joinerDTO.getUid());
            joinerVO.setNickname(joinerDTO.getNickname());
            joinerVO.setAvatar_url(joinerDTO.getUrl());
            joinerVO.setTimestamp(simple.format(joinerDTO.getDate()));
            joinerVO.setIp(joinerDTO.getIp());
            joinerVO.setPrice(joinerDTO.getPrice());

            result.getContent().add(joinerVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list joiners activity_id=%d",activity_id));

        return result;
    }

    /**
     * 获取当前用户参加过的活动记录
     * @param token token
     * @param page 页号
     * @param count 页面数量
     * @return
     *      {
                "content":[
                    {
                        activity_id:"",
                        activity_status:"当前活动的状态,1 进行中 2 抽满 ，待抽奖 3 已开奖 ",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格",
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比",
                        is_winner:true/false
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/me/involved/list",method = RequestMethod.POST)
    public PageableVO<UnderwayActivityVO> listInvolvedByUser(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        long userId = tokenService.validate(token);

        // 接口需要修改
        Pageable<ActivityDTO> pageable = activityService.listMyActivityRecord(userId, page, count);

        PageableVO<UnderwayActivityVO> result = new PageableVO<>();

        UnderwayActivityVO underwayActivityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            underwayActivityVO = new UnderwayActivityVO();
            underwayActivityVO.setActivity_id(activityDTO.getActivityId());
            underwayActivityVO.setActivity_status(activityDTO.getStatus().getNum());
            underwayActivityVO.setActivity_price(activityDTO.getPrice());
            underwayActivityVO.setActivity_real(activityDTO.getRealPrice());
            underwayActivityVO.setActivity_percentage(activityDTO.getPercent());
            underwayActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            underwayActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            underwayActivityVO.setIs_winner(userId == activityDTO.getWinnerId());

            result.getContent().add(underwayActivityVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list involved activities token=%s",token));

        return result;
    }

    /**
     * 获取当前用户参加过活动中开奖中的活动
     * @param token
     * @param page
     * @param count
     * @return
     *      {
                "content":[
                    {
                        activity_id:"",
                        activity_status:"当前活动的状态,1 进行中 2 抽满 ，待抽奖 3 已开奖 ",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格",
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比",
                        is_winner:true/false
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/me/complete/list",method = RequestMethod.POST)
    public PageableVO<UnderwayActivityVO> listCompleteByUser(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        long userId = tokenService.validate(token);

        Pageable<ActivityDTO> pageable = activityService.listMyCompleteActivity(userId, page, count);

        PageableVO<UnderwayActivityVO> result = new PageableVO<>();

        UnderwayActivityVO underwayActivityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            underwayActivityVO = new UnderwayActivityVO();
            underwayActivityVO.setActivity_id(activityDTO.getActivityId());
            underwayActivityVO.setActivity_status(activityDTO.getStatus().getNum());
            underwayActivityVO.setActivity_price(activityDTO.getPrice());
            underwayActivityVO.setActivity_real(activityDTO.getRealPrice());
            underwayActivityVO.setActivity_percentage(activityDTO.getPercent());
            underwayActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            underwayActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            underwayActivityVO.setIs_winner(userId == activityDTO.getWinnerId());

            result.getContent().add(underwayActivityVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list complete activities token=%s",token));

        return result;
    }

    /**
     * 获取当前用户参加过活动已经完成开奖的活动
     * @param token
     * @param page
     * @param count
     * @return
     *      {
                "content":[
                    {
                        activity_id:"",
                        activity_status:"当前活动的状态,1 进行中 2 抽满 ，待抽奖 3 已开奖 ",
                        item_thumbnail_url:"商品缩略图",
                        item_name:"商品名称",
                        activity_price:"活动中商品金币总价格",
                        activity_real:"活动目前已完成价格",
                        activity_percentage:"价格完成百分比",
                        is_winner:true/false
                    }
                ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/me/finish/list",method = RequestMethod.POST)
    public PageableVO<UnderwayActivityVO> listFinishByUser(
            @RequestParam(value = "token",required = true)String token,
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        long userId = tokenService.validate(token);

        Pageable<ActivityDTO> pageable = activityService.listMyFinisheddActivity(userId, page, count);

        PageableVO<UnderwayActivityVO> result = new PageableVO<>();

        UnderwayActivityVO underwayActivityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            underwayActivityVO = new UnderwayActivityVO();
            underwayActivityVO.setActivity_id(activityDTO.getActivityId());
            underwayActivityVO.setActivity_status(activityDTO.getStatus().getNum());
            underwayActivityVO.setActivity_price(activityDTO.getPrice());
            underwayActivityVO.setActivity_real(activityDTO.getRealPrice());
            underwayActivityVO.setActivity_percentage(activityDTO.getPercent());
            underwayActivityVO.setItem_name(activityDTO.getItemDetail().getName());
            underwayActivityVO.setItem_thumbnail_url(activityDTO.getItemThumbnailUrl());
            underwayActivityVO.setIs_winner(userId == activityDTO.getWinnerId());

            result.getContent().add(underwayActivityVO);
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list finish activities token=%s",token));

        return result;
    }

    /**
     * 用户中奖后，对该活动发表回复
     * @param payload
     *      {
                token:"",
                activity_id:"",
                item_id:"",
                content:"",
                imgs:[
                "",""
                ]
            }
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value = "/reply/add",method = RequestMethod.POST)
    public CommonResult addReply(
            @RequestBody String payload)
            throws BizException{
        logger.info(payload);

        try{
            JSONObject jsonObject = new JSONObject(payload);

            long userId = tokenService.validate(jsonObject.getString("token"));

            List<String> imgs = new LinkedList<>();
            if(!jsonObject.isNull("imgs")){
                JSONArray arr = jsonObject.getJSONArray("imgs");

                for(int index = 0; index != arr.length(); index++){
                    imgs.add(arr.getString(index));
                }
            }

            //判断参数是否为空
            if(!(jsonObject.isNull("content")))
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("内容不能为空");

            if(!(jsonObject.isNull("item_id")))
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("商品编号有误");

            if(!(jsonObject.isNull("activity_id")))
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("活动编号有误");

            itemService.addItemReply(jsonObject.getLong("item_id"),
                    jsonObject.getLong("activity_id"),
                    userId,
                    jsonObject.getString("content"),
                    imgs);


            logger.info("add item reply success userId=%d", userId);

            return new CommonResult().setResult(CommonResult.SUCCESS_STATUS).
                    setMessage("回复成功");
        }catch(JSONException jsonException){
            throw jsonException;
        }catch(BizException e){
            if(e.getErrorCode() == BizException.ERROR_CODE_INSTANCE_NOT_FOUND){
                return new CommonResult().setResult(CommonResult.FAILED_STATUS).
                        setMessage("内容不能为空");
            }
            throw e;
        }

    }

    /**
     * 根据item_id获取商品所有晒单
     * @param item_id 商品ID
     * @return
     *      [
                {
                    account_id:1,
                    avatar_url:"",
                    nickname:"",
                    create_at:"发布时间",
                    item_name:"",
                    activity_id:"",
                    content:"",
                    imgs:[
                    "",""
                    ]
                }
            ]
     * @throws BizException
     */
    @RequestMapping(value = "/reply/list",method = RequestMethod.POST)
    public List<ReplyVO> listReply(
            @RequestParam(value = "item_id",required = true)long item_id)
            throws BizException{
        List<ItemReplyDTO> list = itemService.listReply(item_id);

        List<ReplyVO> result = new LinkedList<>();
        ReplyVO replyVO = null;
        for(ItemReplyDTO itemReplyDTO : list){
            replyVO = new ReplyVO();
            replyVO.setAccount_id(itemReplyDTO.getUid());
            replyVO.setActivity_id(itemReplyDTO.getActivityId());
            replyVO.setNickname(itemReplyDTO.getNickName());
            replyVO.setAvatar_url(itemReplyDTO.getAvatarUrl());
            replyVO.setItem_name(itemReplyDTO.getItemName());
            replyVO.setContent(itemReplyDTO.getComment());
            replyVO.setCreate_at(simple.format(itemReplyDTO.getDate()));

            for(String img : itemReplyDTO.getUrls()){
                replyVO.getImgs().add(img);
            }

            result.add(replyVO);
        }

        logger.info(String.format("list reply success item_id=%d",item_id));

        return result;
    }

    @RequestMapping(value = "/winner/list",method = RequestMethod.POST)
    public PageableVO<WinnerVO> listWinner(
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        Pageable<WinnerDTO> pageable = activityService.listWinner(page,count);

        PageableVO<WinnerVO> result = new PageableVO<>();
        WinnerVO winnerVO = null;
        for(WinnerDTO winnerDTO : pageable.getContent()){
            winnerVO = new WinnerVO();
            winnerVO.setNickname(winnerDTO.getNickname());

            long interval = ((new Date()).getTime() - winnerDTO.getTime().getTime())/1000;

            if(interval < 60){
                winnerVO.setTime("距离当前小于分钟");
            }else {
                winnerVO.setTime("距离当前" + interval / 60 + "分钟");
            }
            winnerVO.setDesc(winnerDTO.getDes());

            result.getContent().add(winnerVO);

            logger.info(winnerVO.toString());
        }

        result.setCount(pageable.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info("list winner success");

        return result;
    }
}

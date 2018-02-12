package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.ActivityVO;
import cn.choujiangba.server.admin.vo.PageableVO;
import cn.choujiangba.server.bal.api.ActivityService;
import cn.choujiangba.server.bal.api.TokenService;
import cn.choujiangba.server.bal.dto.ActivityDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 2015/10/25.
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private ActivityService activityService;
    private TokenService tokenSerivce;

    @Autowired
    public ActivityController setActivityService(ActivityService activityService){
        this.activityService = activityService;
        return this;
    }

    @Autowired
    public ActivityController setTokenService(TokenService tokenSerivce){
        this.tokenSerivce = tokenSerivce;
        return this;
    }


    /**
     * 发布普通活动
     * @param item_id 商品ID
     * @param activity_price 活动金币价格
     * @param reward_interval 筹奖完毕后的开奖时间
     * @return
     *      {
                result:"success/failed"
            }
     * @throws BizException
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map<String,String> add(
            @RequestParam(value = "item_id",required = true)long item_id,
            @RequestParam(value = "activity_price",required = true)double activity_price,
            @RequestParam(value = "reward_interval",required = true)String reward_interval)
            throws BizException{
        //将开奖间隔时间转换为秒
        String[] time = reward_interval.split(":");

        long interval = Integer.parseInt(time[0]) * 3600 + //小时转秒
                        Integer.parseInt(time[1]) * 60 + //分钟转秒
                        Integer.parseInt(time[2]); //秒

        activityService.addActivity(item_id,activity_price,interval * 1000);

        Map<String,String> result = new HashMap<>();
        result.put("result","success");

        logger.info(String.format("add activity success item_id=%d",item_id));
        return result;
    }

    /**
     * 获取活动列表
     * @param page 页号
     * @param count 页面数量
     * @return
     *      {
                "content":[
                        {
                            activity_id:"",
                            item_name:"",
                            activity_status:"0=未开始,1=进行中,2=待开奖,3=待发货",
                            activity_price:"活动金币价格"
                        }
                        ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public PageableVO<ActivityVO> list(
            @RequestParam(value = "page",required = false,defaultValue = "1")int page,
            @RequestParam(value = "count",required = false,defaultValue = "10")int count)
            throws BizException{
        //获取正在进行的活动
        Pageable<ActivityDTO> pageable = activityService.listAllActivity(page, count);
        //返回数据
        PageableVO<ActivityVO> result = new PageableVO<>();

        ActivityVO activityVO = null;
        for(ActivityDTO activityDTO : pageable.getContent()){
            activityVO = new ActivityVO();
            activityVO.setActivity_id(activityDTO.getActivityId());
            activityVO.setActivity_price(activityDTO.getPrice());
            activityVO.setActivity_status(activityDTO.getStatus().getNum());
            activityVO.setItem_name(activityDTO.getItemDetail().getName());
            result.getContent().add(activityVO);

            logger.info(activityVO.toString());
        }

        result.setCurrentPage(pageable.getCurrentPage());
        result.setCount(pageable.getContent().size());
        result.setHasPrePage(pageable.getHasPrePage());
        result.setHasNextPage(pageable.getHasNextPage());

        logger.info("list activities success");
        return result;
    }

    /**
     * 活动发货
     * @param activity_id 活动ID
     * @param express_name 快递编号
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value = "/delivery",method = RequestMethod.POST)
    public Map<String,String> delivery(
            @RequestParam(value = "activity_id",required = true)long activity_id,
            @RequestParam(value = "express_name",required = true)String express_name)
            throws BizException{
        logger.info(String.format("activity delivery activity_id=%d,express_name=%s",activity_id, express_name));

        activityService.addDelivery(activity_id, express_name);

        Map<String,String> result = new HashMap<>();
        result.put("result","success");
        result.put("message","添加成功");
        return result;
    }
}

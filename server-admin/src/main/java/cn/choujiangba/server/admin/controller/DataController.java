package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.UserStatisticVO;
import cn.choujiangba.server.bal.api.AccountService;
import cn.choujiangba.server.bal.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 *
 * 数据统计相关接口
 *
 * Author:zhangyu
 * create on 15/10/19.
 */
@RestController
@RequestMapping("/data")
public class DataController {

    private final static Logger logger= LoggerFactory.getLogger(DataController.class);

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/user/get",method = RequestMethod.POST)
    public Map<String,Object> getUserStatistics(
            @RequestParam(value="last_time",required=false)String last_time,
            @RequestParam(value="scope",required=false,defaultValue = "0 ")int scope
    ) throws BizException {
        Map<String,Object> result=new HashMap<>();
        Date end=new Date();
        logger.info("get user statistic last_time ="+last_time+"scope="+scope);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        Date start=null;

        switch (scope){
            case 0:
                //当天的数据
                start = calendar.getTime();
                break;
            case 1:
                //7天内的数据
                calendar.set(Calendar.DAY_OF_WEEK,calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
                start= calendar.getTime();
                break;
            case 2:
                //本月内数据
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                start= calendar.getTime();
                break;
            default:
                start=new Date();
        }

        logger.info(String.format("[start=%s,end=%s]",new SimpleDateFormat("yyyy/MM/dd HH/mm/ss").format(start),new SimpleDateFormat("yyyy/MM/dd HH/mm/ss").format(end)));
        //获取数据
        List<Date> datas = accountService.getUserStatistic(start,end);
        logger.info("successfully get data");
        List<UserStatisticVO> statistic = new LinkedList<>();
        //添加数据
        switch (scope){
            case 0:{
                for(int i=0;i<6;i++){
                    UserStatisticVO user = new UserStatisticVO();
                    user.setIncrease(0);
                    user.setTimestamp(String.format("%d 点",i*4));
                    statistic.add(user);//初始化用户基础数据
                }
                for(Date data:datas){
                    Calendar c = Calendar.getInstance();
                    c.setTime(data);
                    int hour=c.get(Calendar.HOUR_OF_DAY);
                    int timestamp = hour/4;
                    statistic.get(timestamp).setIncrease(statistic.get(timestamp).getIncrease()+1);
                }
                break;
            }
            case 1:{
                for(int i=0;i<7;i++){
                    UserStatisticVO user = new UserStatisticVO();
                    user.setIncrease(0);
                    user.setTimestamp(String.format("星期 %s",i==0?"日":String.valueOf(i)));//0表示星期日，1表示星期1，以此类推
                    statistic.add(user);//初始化用户基础数据
                }
                for(Date data:datas){
                    Calendar c = Calendar.getInstance();
                    c.setTime(data);
                    int day=c.get(Calendar.DAY_OF_WEEK);
                    statistic.get(day-1).setIncrease(statistic.get(day-1).getIncrease()+1);
                }
                break;
            }
            case 2:{
                for(int i=0;i<31;i++){
                    UserStatisticVO user = new UserStatisticVO();
                    user.setIncrease(0);
                    user.setTimestamp(String.valueOf(i));
                    statistic.add(user);//初始化用户基础数据
                }
                for(Date data:datas){
                    Calendar c = Calendar.getInstance();
                    c.setTime(data);
                    int day=c.get(Calendar.DAY_OF_MONTH);
                    statistic.get(day).setIncrease(statistic.get(day).getIncrease()+1);
                }
                break;
            }
            default:break;
        }

        logger.info("successfully zip the data");
        result.put("create_time",new Date().toString());
        result.put("scope",scope);
        result.put("increased_num",datas.size());
        result.put("data",statistic);

        return result;
    }
}

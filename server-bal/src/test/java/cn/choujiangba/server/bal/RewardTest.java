package cn.choujiangba.server.bal;

import cn.choujiangba.server.bal.service.reward.RewardQuene;
import cn.choujiangba.server.bal.service.reward.RewardRequestFactory;
import cn.choujiangba.server.bal.service.reward.SimpleRewardRequestFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 开奖测试
 *
 * Author:zhangyu
 * create on 15/11/6.
 */
public class RewardTest {


    public static void main(String[] args){
        ApplicationContext context=new ClassPathXmlApplicationContext("test-reward.xml");
        RewardRequestFactory requestFactory=context.getBean(RewardRequestFactory.class);
        RewardQuene quene=context.getBean(RewardQuene.class);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.SECOND,5);
        quene.enqueue(requestFactory.build(calendar.getTime(),1));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

}

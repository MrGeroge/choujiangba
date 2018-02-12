package cn.choujiangba.server.concurrent;

import cn.choujiangba.server.dal.api.AccountProfileDao;
import cn.choujiangba.server.dal.po.AccountProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xinghai on 2015/10/20.
 */
@Component
public class UpdateBalanceThread implements Runnable{
    @Autowired
    private AccountProfileDao dao;
    @Override
    public void run() {
        for(int i=0;i<50;i++){
          dao.testUpdateBalance(1,(double)1);
        }
    }

}

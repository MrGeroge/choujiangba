package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.bal.exception.BizException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:zhangyu
 * create on 15/10/20.
 */
public class DataControllerTest {

    private final static Logger logger= LoggerFactory.getLogger(DataController.class);
    @Test
    public void testGetUserStatistics_1() throws BizException {
        DataController dataController=new DataController();
        dataController.getUserStatistics("",0);
    }

    @Test
    public void testGetUserStatistics_2() throws BizException {
        DataController dataController=new DataController();
        dataController.getUserStatistics("",1);
    }

    @Test
    public void testGetUserStatistics_3() throws BizException {
        DataController dataController=new DataController();
        dataController.getUserStatistics("",2);
    }
}

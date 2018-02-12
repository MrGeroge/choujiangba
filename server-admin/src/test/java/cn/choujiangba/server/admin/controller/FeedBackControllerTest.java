package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.config.MvcConfig;
import cn.choujiangba.server.admin.vo.FeedBackVO;
import cn.choujiangba.server.admin.vo.PageableVO;
import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.dto.FeedBackDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.exception.BizException;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

/**
 * Author:shuiyu
 * create on 15/10/20.
 */
@ContextConfiguration(classes = MvcConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FeedBackControllerTest {

    private final static Logger logger= LoggerFactory.getLogger(FeedBackControllerTest.class);
    private FeedBackService feedBackService= EasyMock.createMock(FeedBackService.class);
    private FeedBackController feedBackController = new FeedBackController();
    @Test
    public void testList() throws BizException {
        Pageable<FeedBackDTO> items =new Pageable<>();
        for(int i =0;i<3;i++){
            FeedBackDTO item = new FeedBackDTO();
            item.setContact("123");
            item.setStatus(FeedBackDTO.Status.HANDLING);
            item.setFeedbackId(i);
            item.setContent("123");
            items.getContent().add(item);
        }
        items.setCurrentPage(1);
        items.setHasNextPage(true);
        items.setHasPrePage(true);
        EasyMock.expect(feedBackService.listFeedbackAsStatus(1,5,FeedBackDTO.Status.ALL)).andReturn(items);
        EasyMock.replay(feedBackService);

        feedBackController.setFeedBackService(feedBackService);
        PageableVO<FeedBackVO> result = feedBackController.listFeed(1, 5, 4);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCount(),3);
    }
    @Test
    public void testupdate() throws BizException {
        feedBackService.updateFeedbackStatus(1, FeedBackDTO.Status.HANDLING);
        EasyMock.expectLastCall();
        EasyMock.replay(feedBackService);
        feedBackController.setFeedBackService(feedBackService);
        Map<String,String> result = (Map<String, String>) feedBackController.updateFeedBack(1,1);
        Assert.assertNotNull(result);
    }
}

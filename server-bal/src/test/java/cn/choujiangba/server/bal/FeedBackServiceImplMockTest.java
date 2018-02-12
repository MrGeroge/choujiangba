package cn.choujiangba.server.bal;

import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.dto.FeedBackDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.service.FeedBackServiceImpl;
import cn.choujiangba.server.dal.api.FeedbackDao;
import cn.choujiangba.server.dal.po.Feedback;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class FeedBackServiceImplMockTest {
    private FeedbackDao feedbackDao = EasyMock.createMock(FeedbackDao.class);
    private FeedBackService feedBackService;
    @Test
    public void testListAllFeed(){

        for(int i=0;i<6;i++){
            Feedback feed = new Feedback();
            feed.setContact("18202787422");
            feed.setStatus(0);
            feed.setImg_urls("123123123;213123;123123");
            feed.setContent("hehe dou");
            feed.setId(i);
        }
    }
    @Test
    public void testUpdateStatus() throws BizException {
        Feedback feed1 = new Feedback();
        feed1.setContact("18202787422");
        feed1.setStatus(1);
        feed1.setImg_urls("123123123;213123;123123");
        feed1.setContent("hehe dou");
        feed1.setId(1);

        Feedback feed = new Feedback();
        feed.setContact("18202787422");
        feed.setStatus(0);
        feed.setImg_urls("123123123;213123;123123");
        feed.setContent("hehe dou");
        feed.setId(1);

        EasyMock.expect(feedbackDao.findOne((long)1)).andReturn(feed);
        EasyMock.expect(feedbackDao.save(feed1)).andReturn(feed1);
        EasyMock.replay(feedbackDao);

        feedBackService = new FeedBackServiceImpl().setFeedbackDao(feedbackDao);

        feedBackService.updateFeedbackStatus(1, FeedBackDTO.Status.HANDLING);

    }
    @Test
    public void testAdd() throws BizException {
        Feedback feed = new Feedback();
        feed.setContact("123");
        feed.setStatus(0);
        feed.setImg_urls("123;456;");
        feed.setContent("123");
        EasyMock.expect(feedbackDao.save(feed)).andReturn(feed);
        EasyMock.replay(feedbackDao);

        feedBackService = new FeedBackServiceImpl().setFeedbackDao(feedbackDao);
        List<String> url =new LinkedList<>();
        url.add("123");
        url.add("456");
        feedBackService.addFeed("123","123",url);
    }
}

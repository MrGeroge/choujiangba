package ch.choujiangba.server.app;

import cn.choujiangba.server.app.controller.PublicController;
import cn.choujiangba.server.app.vo.CommonResult;
import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.exception.BizException;
import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/20.
 */
public class PublicControllerTest {

    private PublicController publicController;

    private FeedBackService feedBackService = EasyMock.createMock(FeedBackService.class);

    @Test
    public void testAddFeedBack()throws BizException{
        JSONObject jsonObject = new JSONObject();

        List<String> urls = new LinkedList<>();
        urls.add("www.baidu.com");
        urls.add("www.sino.com");
        urls.add("www.sohu.com");

        JSONArray imgArr = new JSONArray();
        imgArr.put(0, "www.baidu.com");
        imgArr.put(1, "www.sino.com");
        imgArr.put(2, "www.sohu.com");

        jsonObject.put("contact", "18202740055");
        jsonObject.put("text", "反馈意见");
        jsonObject.put("images", imgArr);

        feedBackService.addFeed("反馈意见", "18202740055", urls);
        EasyMock.expectLastCall();

        EasyMock.replay(feedBackService);

        publicController = new PublicController().
                setFeedBackService(feedBackService);

        CommonResult commonResult = publicController.addFeedback(jsonObject.toString());

        Assert.assertEquals(CommonResult.SUCCESS_STATUS, commonResult.getResult());
        Assert.assertEquals("添加反馈成功", commonResult.getMessage());
    }
}

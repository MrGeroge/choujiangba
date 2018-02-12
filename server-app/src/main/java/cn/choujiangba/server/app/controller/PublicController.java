package cn.choujiangba.server.app.controller;

import cn.choujiangba.server.app.vo.CommonResult;
import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.exception.BizException;
import net.sf.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao on 2015/10/20.
 */
@RestController
@RequestMapping("/public")
public class PublicController extends BaseController{

    private static Logger logger= LoggerFactory.getLogger(PublicController.class);

    private FeedBackService feedBackService;

    @Autowired
    public PublicController setFeedBackService(FeedBackService feedBackService){
        this.feedBackService = feedBackService;
        return this;
    }

    /**
     * 添加一条反馈
     * @param payload
     *      {
                "contact":"",
                "text":" 文本内容 ",
                "images":[
                        "",""
                    ]
            }
     * @return { result:"success" }
     * @throws BizException
     */
    @RequestMapping(value="/feedback/add", method= RequestMethod.POST)
    public CommonResult addFeedback(@RequestBody String payload)throws BizException{

        logger.info(payload);

        try{
            JSONObject jsonObject = new JSONObject(payload);

            List<String> urls = new LinkedList<>();

            //将images的json数组转为用';'分隔的字符串
            if(!jsonObject.isNull("images")){
                JSONArray arr = jsonObject.getJSONArray("images");

                for(int i = 0; i != arr.length(); i++){
                    String imgUrl = arr.getString(i);

                    urls.add(imgUrl);
                }
            }

            feedBackService.addFeed(jsonObject.getString("text"),
                    jsonObject.getString("contact"),
                    urls);

            return new CommonResult().
                    setResult(CommonResult.SUCCESS_STATUS).
                    setMessage("添加反馈成功");
        }catch(JSONException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}

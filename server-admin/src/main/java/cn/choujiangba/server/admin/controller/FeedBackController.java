package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.FeedBackVO;
import cn.choujiangba.server.admin.vo.PageableVO;
import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.dto.FeedBackDTO;
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
 * Created by shuiyu on 2015/10/20.
 */
@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    private FeedBackService feedBackService;
    private final static Logger logger= LoggerFactory.getLogger(DataController.class);

    @Autowired
    public void setFeedBackService(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }
    @RequestMapping(value="list",method = RequestMethod.POST)
    public PageableVO<FeedBackVO> listFeed(@RequestParam(value="page",required = false,defaultValue = "1")int page,
                                         @RequestParam(value="count",required = false,defaultValue = "20")int count,
                                         @RequestParam(value="status",required = false,defaultValue = "4")int status)throws BizException{
        PageableVO<FeedBackVO> feedback=new PageableVO<>();
        logger.info(String.format("list feed page=%d count=%d status=%d",page,count,status));
        FeedBackDTO.Status statusEnum;
        switch (status){
            case 0:
                statusEnum=FeedBackDTO.Status.NOT_HANDLE;
                break;
            case 1:
                statusEnum=FeedBackDTO.Status.HANDLING;
                break;
            case 2:
                statusEnum=FeedBackDTO.Status.SOLVED;
                break;
            case 3:
                statusEnum=FeedBackDTO.Status.IGNORE;
                break;
            default:statusEnum= FeedBackDTO.Status.ALL;
        }
        Pageable<FeedBackDTO> items = feedBackService.listFeedbackAsStatus(page,count,statusEnum);
        logger.info("Successfully get data from service");
        feedback.setCurrentPage(items.getCurrentPage());
        feedback.setHasNextPage(items.getHasNextPage());
        feedback.setHasPrePage(items.getHasPrePage());

        for(FeedBackDTO item:items.getContent()){
            FeedBackVO feedBackVO = new FeedBackVO();
            feedBackVO.setContact(item.getContact());
            feedBackVO.setContent(item.getContent());
            feedBackVO.setStatus(item.getStatus().getNum());
            feedBackVO.setFeedback_id(item.getFeedbackId());
            feedBackVO.getImgs().addAll(item.getImgs());
            feedback.getContent().add(feedBackVO);
        }
        logger.info("successfully zipped data");
        feedback.setCount(feedback.getContent().size());
        return feedback;
    }
    @RequestMapping(value="/status/update",method = RequestMethod.POST)
    public Object updateFeedBack(@RequestParam(value="feedback_id",required = true)int feedbackId,
                                 @RequestParam(value="status",required = true)int status)throws BizException{
        FeedBackDTO.Status statusEnum;
        switch (status){
            default:
            case 0:
                statusEnum=FeedBackDTO.Status.NOT_HANDLE;
                break;
            case 1:
                statusEnum=FeedBackDTO.Status.HANDLING;
                break;
            case 2:
                statusEnum=FeedBackDTO.Status.SOLVED;
                break;
            case 3:
                statusEnum=FeedBackDTO.Status.IGNORE;
                break;
        }
        feedBackService.updateFeedbackStatus(feedbackId,statusEnum);
        Map<String,String>result = new HashMap<>();
        result.put("result","success");
        result.put("message","更新成功");
        return result;
    }
}

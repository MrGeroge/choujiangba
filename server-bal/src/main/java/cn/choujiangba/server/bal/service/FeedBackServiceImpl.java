package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.FeedBackService;
import cn.choujiangba.server.bal.dto.FeedBackDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.dal.api.FeedbackDao;
import cn.choujiangba.server.dal.po.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by shuiyu on 2015/10/20.
 */
public class FeedBackServiceImpl implements FeedBackService {
    private FeedbackDao feedbackDao;

    private static Logger logger= LoggerFactory.getLogger(FeedBackServiceImpl.class);
    @Autowired
    public FeedBackServiceImpl setFeedbackDao(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
        return this;
    }

        @Override
    public void addFeed(String content, String contact, List<String> urls) throws BizException {
        if(content==null||"".equals(content))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"content is null");
        if(contact==null||"".equals(contact))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"contact is null");
        if(urls==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"urls is null");
        String url="";
        for(String item:urls){
            url=url+item+";";
        }
        Feedback feed = new Feedback();
        feed.setContent(content);
        feed.setContact(contact);
        feed.setImg_urls(url);
        feed.setStatus(0);
        feedbackDao.save(feed);
    }

    @Override
    public Pageable<FeedBackDTO> listFeedbackAsStatus(int page, int count, FeedBackDTO.Status status) throws BizException {
        logger.info("list  feedBack as status = "+status.getNum());
        Pageable<FeedBackDTO> feedsdto = new Pageable<>();
        Page<Feedback> feedbacks;
        if(page < 1 || count < 1||count>50){
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID, String.format("page=%d,count=%d is not valid",page,count));
        }

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if(status.getNum()==4){
         feedbacks = feedbackDao.findAll(new PageRequest(page - 1, count, sort));
        }else {
             feedbacks = feedbackDao.findByStatus(status.getNum(), new PageRequest(page - 1, count, sort));
        }
        boolean hasNextPage = false;
        boolean hasPrePage = false;

        logger.info(String.format("successfully get feedback from database page = %d count = %d there are totally %d items",page,count,feedbacks.getTotalElements()));

        feedsdto.setHasNextPage(feedbacks.hasNext());
        feedsdto.setHasPrePage(feedbacks.hasPrevious());
        feedsdto.setCurrentPage(page);

        for(Feedback item:feedbacks){
            FeedBackDTO feedBack = new FeedBackDTO();
            feedBack.setContent(item.getContent());
            feedBack.setContact(item.getContact());
            feedBack.setFeedbackId(item.getId());
            String[] urls = item.getImg_urls().split(";");
            for(String url:urls)
                feedBack.getImgs().add(url);
            switch (item.getStatus()){
                case 0:
                    feedBack.setStatus(FeedBackDTO.Status.NOT_HANDLE);
                    break;
                case 1:
                    feedBack.setStatus(FeedBackDTO.Status.HANDLING);
                    break;
                case 2:
                    feedBack.setStatus(FeedBackDTO.Status.SOLVED);
                    break;
                case 3:
                    feedBack.setStatus(FeedBackDTO.Status.IGNORE);
                    break;
                default:break;
            }
            feedsdto.getContent().add(feedBack);
        }
        logger.info("successfully zipped the data");
        return feedsdto;
    }

    @Override
    public void updateFeedbackStatus(long feedId, FeedBackDTO.Status status) throws BizException {
        if(feedId<1)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"feedId is not correct");
        Feedback feed = feedbackDao.findOne(feedId);
        if(feed==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"there is no such feed data");
        feed.setStatus(status.getNum());
        feedbackDao.save(feed);
    }
}

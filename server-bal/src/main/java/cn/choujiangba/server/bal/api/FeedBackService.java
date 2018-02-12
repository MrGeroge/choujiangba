package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.dto.FeedBackDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.exception.BizException;

import java.util.List;

/**
 * Created by shuiyu on 2015/10/19.
 */
public interface FeedBackService {

    /**
     * 用户添加一条反馈
     * @param content 反馈内容
     * @param contact 联系方式
     * @param urls
     * @throws BizException error_code包括
     *              ERROR_CODE_FIELD_NOT_NULL
     */
    void addFeed(String content,String contact,List<String> urls)throws BizException;

    /**
     * 根据反馈状态来获取反馈
     * @param page 页数
     * @param count 每页多少个
     * @param status 状态
     * @return
     * @throws BizException error_code包括ERROR_CODE_PARAMETER_NOT_VALID
     */
    Pageable<FeedBackDTO> listFeedbackAsStatus(int page,int count,FeedBackDTO.Status status)throws BizException;

    /**
     * 更改反馈状态
     * @param feedId 反馈的id
     * @param status 要更改去的状态
     * @throws BizException error_code包括
     *          ERROR_CODE_INSTANCE_NOT_FOUND
     *          ERROR_CODE_FIELD_NOT_NULL
     */
    void updateFeedbackStatus(long feedId,FeedBackDTO.Status status)throws BizException;
}

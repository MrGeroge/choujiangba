package cn.choujiangba.server.bal.api;

import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.dal.po.ItemReply;

import java.io.InputStream;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/21.
 */
public interface ItemService {
    /**
     * 上传图片
     * @param itemId NOT NULL
     * @param file NOT NULL文件输入流
     * @parma fileLength NOT NULL文件长度
     * @throws BizException
     */
    String uploadImg(long itemId,InputStream file,long fileLength)throws BizException;

    /**
     * 删除商品
     * @param itemId NOT NULL
     * @throws BizException
     */
    void deleteItem(long itemId)throws BizException;

    /**
     * 添加商品
     * @param name 商品名称
     * @param price 商品价格
     * @param properties 属性
     * @param thumbnailUrl 缩略图
     * @param imgUrl   商品图片
     * @param detail 图文详情
     * @throws BizException
     */
    void addItem(String name,int price,
                 List<String> properties,
                 String thumbnailUrl,
                 List<String>imgUrl,
                 String detail)throws BizException;

    /**
     * 列出商品
     * @param page 页数
     * @param count 每页多少个
     * @return
     * @throws BizException
     */
    Pageable<ItemSimpleDTO> listItem(int page,int count)throws BizException;

    /**
     * 查询商品
     * @param keyword NOT NULL关键字
     * @return
     * @throws BizException
     */
    List<ItemSimpleDTO> searchItemByName(String keyword)throws BizException;

    /**
     * 查询商品
     * @param keyword NOT NULL关键字
     * @return
     * @throws BizException
     */
    List<ItemSimpleDTO> searchItemByPro(String keyword)throws BizException;

    /**
     * 得到统计信息
     * @param itemId NOT NULL
     * @return
     * @throws BizException
     */
    ItemStatisticDTO getItemStatistic(long itemId)throws BizException;

    /**
     * 得到商品基础信息
     * @param itemId NOT NULL
     * @return
     * @throws BizException
     */
    ItemDetailDTO getItem(long itemId)throws BizException;

    /**
     * 添加商品评论
     * @param itemId NOT NULL
     * @param activityId NOT NULL 活动id
     * @param uid NOT NULL 用户id
     * @param comment NOT NULL 评论本体
     * @param urls 可空 评论的图片urls
     * @throws BizException
     */
    boolean addItemReply(long itemId,long activityId,long uid,String comment,List<String>urls)throws BizException;



    /**
     * 列出分页的商品查看记录
     * @param page NOT NULL 页
     * @param count NOT NULL 没页几个
     * @return
     * @throws BizException
     */
    Pageable<ItemViewStatisticDTO> listAllViewStatistic(int page,int count)throws  BizException;

    /**
     * 列出所有的商品购买记录
     * @param page NOT NULL 页
     * @param count NOT NULL 每页多少个
     * @return
     * @throws BizException
     */
    Pageable<ItemPurchaseStatisticDTO> listAllPurchaseStatistic(int page,int count)throws BizException;

    /**
     * 列出商品的评论记录
     * @param page NOT NULL
     * @param count NOT NULL
     * @param itemId NOT NULL
     * @return
     * @throws BizException
     */
    Pageable<ItemReplyDTO>listReplyByItemId(int page,int count,long itemId)throws BizException;

    /**
     * 列出商品的评论记录，不分页
     * @param itemId NOT NULL
     * @return
     * @throws BizException
     */
    List<ItemReplyDTO> listReply(long itemId)throws BizException;

    /**
     * 列出商品的评论记录
     * @param activityId NOT NULL
     * @return
     * @throws BizException
     */
    ItemReplyDTO listReplyByActivityId(long activityId)throws BizException;


    /**
     * 添加热门商品
     * @param itemId NOT NULL 商品id
     * @param banner NOT NULL 描述图片
     * @throws BizException
     */
     boolean addHotItem(long itemId,String banner)throws BizException;

    /**
     * 删除热门商品
     * @param hotId
     * @throws BizException
     */
     boolean deleteHotItem(long hotId)throws BizException;

    /**
     * 获取所有的热门商品
     * @return
     * @throws BizException
     */
     List<HotItemDTO> listAllHot()throws BizException;

    /**
     * 新增一条查看记录
     * @param uid NOT NULL
     * @param itemId NOT NULL
     * @param city
     * @param district
     * @param longitude
     * @param latitude
     * @param ip NOT NULL
     * @return
     * @throws BizException
     */
    boolean addItemViewRecord(long uid,long itemId,String city,String district,double longitude,double latitude,String ip)throws BizException;


}

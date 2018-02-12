package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.ItemHotVO;
import cn.choujiangba.server.admin.vo.ItemVO;
import cn.choujiangba.server.admin.vo.PageableVO;
import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/10/21.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    private final static Logger logger= LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;

    @Autowired
    public ItemController setItemService(ItemService itemService){
        this.itemService = itemService;
        return this;
    }

    /**
     * 增加商品
     * @param payload
     *      {
                name:"商品名称",
                price:"商品价格",
                properties:[
                        "",""
                ],
                thumbnail_url:"缩略图",
                image_urls:[
                        "",""
                ],
                detail:"商品图文详情,富文本"
            }
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map<String,String> add(@RequestBody String payload)
            throws BizException{
        logger.info(payload);

        Map<String,String> result = new HashMap<>();

        try{
            JSONObject jsonObject = new JSONObject(payload);

            List<String> properties = new LinkedList<>();
            List<String> image_urls = new LinkedList<>();

            //取出商品属性集合
            if(!jsonObject.isNull("properties")){
                JSONArray proArr = jsonObject.getJSONArray("properties");

                for(int i = 0 ;i != proArr.length();i++){
                    properties.add(proArr.getString(i));
                }
            }

            //取出商品图片路径集合
            if(!jsonObject.isNull("image_urls")){
                JSONArray imgArr = jsonObject.getJSONArray("image_urls");

                for(int i = 0; i != imgArr.length() ;i++){
                    image_urls.add(imgArr.getString(i));
                }
            }

            itemService.addItem(jsonObject.getString("name"),
                    Integer.parseInt(jsonObject.getString("price")),
                    properties,
                    jsonObject.getString("thumbnail_url"),
                    image_urls,
                    jsonObject.getString("detail"));

            result.put("result", "success");
            result.put("message", "添加成功");

            logger.info(String.format("add item success name=%s",jsonObject.getString("name")));

            return result;
        }catch (JSONException e){
            throw e;
        }catch (Exception e){
            throw e;
        }

    }

    /**
     * 得到商品详细信息
     * @param item_id itemID
     * @param statistic 是否有统计数据
     * @return
     *      {
                base:{
                    item_id:""
                    name:"",
                    price:"",
                    property:["",""],
                    detail:"",
                    thumbnail_url:"",
                    desc_img_urls:["",""]
                },
                statistics:{
                    activity_num:"发布活动数目",
                    view_num:"商品显示数目",
                    reply_num:"商品晒单数目"
                }
            }
     * @throws BizException
     */
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Map<String,Object> get(
            @RequestParam(value="item_id",required = true)long item_id,
            @RequestParam(value="statistic",required = false,defaultValue = "false")boolean statistic)
            throws BizException {
        Map<String,Object> result = new HashMap<>();//用于保存返回结果
        Map<String,Object> base = new HashMap<>();
        Map<String,String> statistics = new HashMap<>();

        //基础信息
        ItemDetailDTO itemDetailDTO = itemService.getItem(item_id);

        //统计信息
        ItemStatisticDTO itemStatisticDTO = itemService.getItemStatistic(item_id);

        //将基本信息保存到map中
        base.put("item_id",itemDetailDTO.getItemId());
        base.put("name",itemDetailDTO.getName());
        base.put("price",itemDetailDTO.getPrice());
        base.put("property",itemDetailDTO.getProperty());
        base.put("detail",itemDetailDTO.getDetail());
        base.put("thumbnail_url",itemDetailDTO.getThumbnailUrl());
        base.put("desc_img_urls",itemDetailDTO.getDescImgUrl());

        //将统计信息保存到map中
        statistics.put("activity_num",String.valueOf(itemStatisticDTO.getActivityNum()));
        statistics.put("view_num",String.valueOf(itemStatisticDTO.getViewNum()));
        statistics.put("reply_num",String.valueOf(itemStatisticDTO.getReplyNum()));

        result.put("base",base);
        result.put("statistics",statistics);

        logger.info(String.format("get item success item_id= %d",item_id));

        return result;
    }

    /**
     * 商品查询,目前只查询商品名称、商品属性，且不分页
     * @param keywords 关键字
     * @return
     *      [
                {
                    item_id:1,
                    activities_num:"已经发布关连的活动数量",
                    item_name:"商品名称",
                    item_thumbnail_url:"商品缩略图链接",
                }
            ]
     * @throws BizException
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public List<ItemVO> search(
            @RequestParam(value="keywords",required = true)String keywords)
        throws BizException{
        List<ItemVO> result = new LinkedList<>();

        List<ItemSimpleDTO> listByName = itemService.searchItemByName(keywords);//根据name搜索
        List<ItemSimpleDTO> listByPro = itemService.searchItemByPro(keywords);//根据property搜索

        listByName.removeAll(listByPro);//进行去重操作

        ItemVO itemVO = null;
        //将根据name搜索的list结果加到result中
        if(listByName.size() != 0){
            for(ItemSimpleDTO itemSimpleDTO : listByName){
                itemVO = new ItemVO();
                itemVO.setItem_id(itemSimpleDTO.getItemId());
                itemVO.setItem_name(itemSimpleDTO.getName());
                itemVO.setItem_thumbnail_url(itemSimpleDTO.getThumbnailUrl());
                itemVO.setActivities_num(String.valueOf((itemSimpleDTO.getActivityNum())));
                result.add(itemVO);
            }
        }

        //将根据property搜索的list结果加到result中
        if(listByPro.size() != 0){
            for(ItemSimpleDTO itemSimpleDTO : listByPro){
                itemVO = new ItemVO();
                itemVO.setItem_id(itemSimpleDTO.getItemId());
                itemVO.setItem_name(itemSimpleDTO.getName());
                itemVO.setItem_thumbnail_url(itemSimpleDTO.getThumbnailUrl());
                itemVO.setActivities_num(String.valueOf((itemSimpleDTO.getActivityNum())));
                result.add(itemVO);
            }
        }

        logger.info(String.format("search items success keywords=%s",keywords));
        return result;
    }

    /**
     * 删除商品，如果存在活动则不能删除
     * @param item_id itemId
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public Map<String,String> delete(
            @RequestParam(value="item_id",required = true)long item_id)
        throws BizException{
        Map<String,String> result = new HashMap<>();

        itemService.deleteItem(item_id);
        result.put("result", "success");
        result.put("message","删除成功");

        logger.info(String.format("delete item success item_id= %d",item_id));
        return result;
    }

    /**
     * 列出商品
     * @param page 页数
     * @param count 页面大小
     * @return
     *      {
                "content":[
                            {
                                item_id:1,
                                activities_num:"已经发布关连的活动数量",
                                item_name:"商品名称",
                                item_thumbnail_url:"商品缩略图链接",
                            }
                        ]
                "currentPage":1,
                "count":10,
                "hasNextPage":true,
                "hasPrePage":true
            }
     * @throws BizException
     */
    @RequestMapping(value="/list",method = RequestMethod.POST)
    public PageableVO<ItemVO> list(
            @RequestParam(value="page",required = false,defaultValue = "1")int page,
            @RequestParam(value="count",required = false,defaultValue = "10")int count)
        throws BizException{
        PageableVO<ItemVO> result = new PageableVO<ItemVO>();

        Pageable<ItemSimpleDTO> pageable = itemService.listItem(page,count);

        //将ItemSimpleDTO改为ItemVO类型并存到result中
        ItemVO itemVO = null;
        if(pageable.getContent().size() != 0) {
            for (ItemSimpleDTO itemSimpleDTO : pageable.getContent()) {
                itemVO = new ItemVO();
                itemVO.setItem_id(itemSimpleDTO.getItemId());
                itemVO.setItem_name(itemSimpleDTO.getName());
                itemVO.setActivities_num(String.valueOf(itemSimpleDTO.getActivityNum()));
                itemVO.setItem_thumbnail_url(itemSimpleDTO.getThumbnailUrl());
                result.getContent().add(itemVO);
            }
        }

        result.setCount(result.getContent().size());
        result.setCurrentPage(pageable.getCurrentPage());
        result.setHasNextPage(pageable.getHasNextPage());
        result.setHasPrePage(pageable.getHasPrePage());

        logger.info(String.format("list items success page[count=%d,currentPage=%d,hasNextPage=%b,hasPrePage=%b]",
                result.getContent().size(),
                pageable.getCurrentPage(),
                pageable.getHasNextPage(),
                pageable.getHasPrePage()));
        return result;
    }

    /**
     * 获取热销商品id集合
     * @return
     *      [
                1,2,3,4,5
            ]
     * @throws BizException
     */
    @RequestMapping(value = "/hot/ids/list",method = RequestMethod.POST)
    public List<Long> listHotIds()throws BizException{
        List<HotItemDTO> hotList = itemService.listAllHot();

        List<Long> result = new LinkedList<>();
        for(HotItemDTO hotItemDTO : hotList){
            result.add(hotItemDTO.getItemId());
            logger.info(String.valueOf(hotItemDTO.getItemId()));
        }

        return result;
    }

    /**
     * 获取热销商品
     * @return
     *      [
                {
                item_id:1,
                item_name:"商品名称",
                banner_url:""
                }
            ]
     * @throws BizException
     */
    @RequestMapping(value = "/hot/list",method = RequestMethod.POST)
    public List<ItemHotVO> listHot()throws BizException{
        List<HotItemDTO> hotList = itemService.listAllHot();

        List<ItemHotVO> result = new LinkedList<>();
        ItemHotVO itemHotVO = null;
        ItemDetailDTO itemDetailDTO = null;
        for(HotItemDTO hotItemDTO : hotList){
            itemHotVO = new ItemHotVO();
            itemHotVO.setId(hotItemDTO.getId());
            itemHotVO.setItem_id(hotItemDTO.getItemId());
            itemDetailDTO = itemService.getItem(hotItemDTO.getItemId());
            itemHotVO.setItem_name(itemDetailDTO.getName());
            itemHotVO.setBanner_url(hotItemDTO.getBanner());

            logger.info(itemHotVO.toString());

            result.add(itemHotVO);
        }

        return result;
    }

    /**
     * 添加热销商品
     * @param item_id 商品ID
     * @param banner_url
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value = "/hot/add",method = RequestMethod.POST)
    public Map<String,String> addHot(
            @RequestParam(value = "item_id",required = true)long item_id,
            @RequestParam(value = "banner_url",required = true)String banner_url)
            throws BizException{
        itemService.addHotItem(item_id, banner_url);

        Map<String,String> result = new HashMap<>();
        result.put("result","success");
        result.put("message","添加成功");

        return result;
    }

    /**
     * 移除热销商品
     * @param id
     * @return
     *      {
                result:"success/failed",
                message:""
            }
     * @throws BizException
     */
    @RequestMapping(value = "/hot/delete",method = RequestMethod.POST)
    public Map<String,String> deleteHot(
            @RequestParam(value = "id",required = true)long id)
            throws BizException{
        itemService.deleteHotItem(id);

        Map<String,String> result = new HashMap<>();
        result.put("result","success");
        result.put("message","添加成功");

        return result;
    }

}

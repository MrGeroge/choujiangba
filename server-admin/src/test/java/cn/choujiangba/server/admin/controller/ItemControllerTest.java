package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.vo.ItemVO;
import cn.choujiangba.server.admin.vo.PageableVO;
import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.dto.ItemDetailDTO;
import cn.choujiangba.server.bal.dto.ItemSimpleDTO;
import cn.choujiangba.server.bal.dto.ItemStatisticDTO;
import cn.choujiangba.server.bal.dto.Pageable;
import cn.choujiangba.server.bal.exception.BizException;
import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/10/21.
 */
public class ItemControllerTest {

    private ItemController itemController;
    private ItemService itemService = EasyMock.createMock(ItemService.class);
    private static final Logger logger = LoggerFactory.getLogger(ItemControllerTest.class);

    @Test
    public void testAdd()throws BizException{
        JSONObject jsonObject = new JSONObject();

        List<String> properties = new LinkedList<>();
        properties.add("pro1");
        properties.add("pro2");
        properties.add("pro3");

        List<String> urls = new LinkedList<>();
        urls.add("www.baidu.com");
        urls.add("www.sino.com");
        urls.add("www.sohu.com");

        JSONArray imgArr = new JSONArray();
        imgArr.put(0, "www.baidu.com");
        imgArr.put(1, "www.sino.com");
        imgArr.put(2, "www.sohu.com");

        jsonObject.put("name", "zs");
        jsonObject.put("price", "12");
        jsonObject.put("properties", properties);
        jsonObject.put("thumbnail_url", "thumbnail_url");
        jsonObject.put("image_urls", imgArr);
        jsonObject.put("detail", "detail");

        itemService.addItem("zs", 12, properties, "thumbnail_url", urls, "detail");
        EasyMock.expectLastCall();

        EasyMock.replay(itemService);

        itemController = new ItemController().setItemService(itemService);

        Map<String,String> result = itemController.add(jsonObject.toString());

        //logger.info(jsonObject.toString());
        Assert.assertEquals("success",result.get("result"));
    }

    @Test
    public void testGet()throws BizException{
        ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
        itemDetailDTO.setItemId(1);
        itemDetailDTO.setName("iphone");
        itemDetailDTO.setDetail("a phone");
        itemDetailDTO.setPrice(222);
        itemDetailDTO.getProperty().add("properties1");
        itemDetailDTO.getProperty().add("properties2");
        itemDetailDTO.getProperty().add("properties3");
        itemDetailDTO.setThumbnailUrl("url");
        itemDetailDTO.getDescImgUrl().add("url1");
        itemDetailDTO.getDescImgUrl().add("url2");
        itemDetailDTO.getDescImgUrl().add("url3");

        ItemStatisticDTO itemStatisticDTO = new ItemStatisticDTO();
        itemStatisticDTO.setViewNum(3);
        itemStatisticDTO.setActivityNum(4);
        itemStatisticDTO.setReplyNum(5);

        EasyMock.expect(itemService.getItem(1)).andReturn(itemDetailDTO);
        EasyMock.expect(itemService.getItemStatistic(1)).andReturn(itemStatisticDTO);

        EasyMock.replay(itemService);

        itemController = new ItemController().
                setItemService(itemService);

        Map<String,Object> result = itemController.get(1, false);
        Map<String,Object> base = (Map<String,Object>)result.get("base");
        Map<String,String> statistics = (Map<String,String>)result.get("statistics");
        List<String> property = (List<String>)base.get("property");
        List<String> urls = (List<String>)base.get("desc_img_urls");
        logger.info(String.format("item[name= %s,detail= %s,price= %s,thumbnailUrl= %s,properties1= %s, url1= %s,activityNum= %s]",
                base.get("name"),base.get("detail"),base.get("price"),
                base.get("thumbnail_url"),
                property.get(0),urls.get(0),statistics.get("activity_num")));
    }

    @Test
    public void testSearch()throws BizException{
        ItemSimpleDTO itemSimpleDTO1 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO2 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO3 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO4 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO41 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO5 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO6 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO7 = new ItemSimpleDTO();

        itemSimpleDTO1.setItemId(1);
        itemSimpleDTO2.setItemId(2);
        itemSimpleDTO3.setItemId(3);
        itemSimpleDTO4.setItemId(4);
        itemSimpleDTO41.setItemId(4);
        itemSimpleDTO5.setItemId(5);
        itemSimpleDTO6.setItemId(6);
        itemSimpleDTO7.setItemId(7);
        itemSimpleDTO1.setName("item");
        itemSimpleDTO2.setName("item");
        itemSimpleDTO3.setName("item");
        itemSimpleDTO4.setName("item");
        itemSimpleDTO41.setName("item");
        itemSimpleDTO5.setName("item5");
        itemSimpleDTO6.setName("item6");
        itemSimpleDTO7.setName("item7");
        itemSimpleDTO1.setThumbnailUrl("thumbnailUrl1");
        itemSimpleDTO2.setThumbnailUrl("thumbnailUrl2");
        itemSimpleDTO3.setThumbnailUrl("thumbnailUrl3");
        itemSimpleDTO4.setThumbnailUrl("thumbnailUrl4");
        itemSimpleDTO41.setThumbnailUrl("thumbnailUrl4");
        itemSimpleDTO5.setThumbnailUrl("thumbnailUrl5");
        itemSimpleDTO6.setThumbnailUrl("thumbnailUrl6");
        itemSimpleDTO7.setThumbnailUrl("thumbnailUrl7");
        itemSimpleDTO1.setActivityNum(3);
        itemSimpleDTO2.setActivityNum(4);
        itemSimpleDTO3.setActivityNum(5);
        itemSimpleDTO4.setActivityNum(6);
        itemSimpleDTO41.setActivityNum(6);
        itemSimpleDTO5.setActivityNum(7);
        itemSimpleDTO6.setActivityNum(8);
        itemSimpleDTO7.setActivityNum(9);

        List<ItemSimpleDTO> listByName = new LinkedList<>();
        listByName.add(itemSimpleDTO1);
        listByName.add(itemSimpleDTO2);
        listByName.add(itemSimpleDTO3);
        listByName.add(itemSimpleDTO4);

        List<ItemSimpleDTO> listByPro = new LinkedList<>();
        listByPro.add(itemSimpleDTO41);
        listByPro.add(itemSimpleDTO5);
        listByPro.add(itemSimpleDTO6);
        listByPro.add(itemSimpleDTO7);

        EasyMock.expect(itemService.searchItemByName("item")).andReturn(listByName);
        EasyMock.expect(itemService.searchItemByPro("item")).andReturn(listByPro);

        EasyMock.replay(itemService);

        itemController = new ItemController().
                setItemService(itemService);

        List<ItemVO> result = itemController.search("item");

        for(ItemVO itemVO : result) {
            logger.info(String.format("item[id = %d, name = %s, thumbnailUrl = %s, activityNum = %s]",
                    itemVO.getItem_id(),itemVO.getItem_name(),
                    itemVO.getItem_thumbnail_url(),itemVO.getActivities_num()));
        }

        Assert.assertNotNull(result);
    }

    @Test
    public void testDelete()throws BizException{

    }

    @Test
    public void testList()throws BizException{
        Pageable<ItemSimpleDTO> pageable = new Pageable<>();

        ItemSimpleDTO itemSimpleDTO1 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO2 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO3 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO4 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO5 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO6 = new ItemSimpleDTO();
        ItemSimpleDTO itemSimpleDTO7 = new ItemSimpleDTO();

        itemSimpleDTO1.setItemId(1);
        itemSimpleDTO2.setItemId(2);
        itemSimpleDTO3.setItemId(3);
        itemSimpleDTO4.setItemId(4);
        itemSimpleDTO5.setItemId(5);
        itemSimpleDTO6.setItemId(6);
        itemSimpleDTO7.setItemId(7);
        itemSimpleDTO1.setName("item");
        itemSimpleDTO2.setName("item");
        itemSimpleDTO3.setName("item");
        itemSimpleDTO4.setName("item");
        itemSimpleDTO5.setName("item5");
        itemSimpleDTO6.setName("item6");
        itemSimpleDTO7.setName("item7");
        itemSimpleDTO1.setThumbnailUrl("thumbnailUrl1");
        itemSimpleDTO2.setThumbnailUrl("thumbnailUrl2");
        itemSimpleDTO3.setThumbnailUrl("thumbnailUrl3");
        itemSimpleDTO4.setThumbnailUrl("thumbnailUrl4");
        itemSimpleDTO5.setThumbnailUrl("thumbnailUrl5");
        itemSimpleDTO6.setThumbnailUrl("thumbnailUrl6");
        itemSimpleDTO7.setThumbnailUrl("thumbnailUrl7");
        itemSimpleDTO1.setActivityNum(3);
        itemSimpleDTO2.setActivityNum(4);
        itemSimpleDTO3.setActivityNum(5);
        itemSimpleDTO4.setActivityNum(6);
        itemSimpleDTO5.setActivityNum(7);
        itemSimpleDTO6.setActivityNum(8);
        itemSimpleDTO7.setActivityNum(9);

        pageable.getContent().add(itemSimpleDTO1);
        pageable.getContent().add(itemSimpleDTO2);
        pageable.getContent().add(itemSimpleDTO3);
        pageable.getContent().add(itemSimpleDTO4);
        pageable.getContent().add(itemSimpleDTO5);
        pageable.getContent().add(itemSimpleDTO6);
        pageable.getContent().add(itemSimpleDTO7);

        pageable.setCurrentPage(1);
        pageable.setHasNextPage(false);
        pageable.setHasPrePage(false);

        EasyMock.expect(itemService.listItem(1,10)).andReturn(pageable);

        EasyMock.replay(itemService);

        itemController  = new ItemController().
                setItemService(itemService);

        PageableVO<ItemVO> result = itemController.list(1,10);

        for(ItemVO itemVO : result.getContent()){
            logger.info(String.format("item[id = %d, name = %s, thumbnailUrl = %s, activityNum = %s]",
                    itemVO.getItem_id(),itemVO.getItem_name(),
                    itemVO.getItem_thumbnail_url(),itemVO.getActivities_num()));
        }

        Assert.assertNotNull(result);
    }
}

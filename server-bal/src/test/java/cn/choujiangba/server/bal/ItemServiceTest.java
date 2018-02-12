package cn.choujiangba.server.bal;

import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.dto.ItemDetailDTO;
import cn.choujiangba.server.bal.dto.ItemSimpleDTO;
import cn.choujiangba.server.bal.dto.ItemStatisticDTO;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.service.ItemServiceImpl;
import cn.choujiangba.server.dal.api.ItemBaseStatisticDao;
import cn.choujiangba.server.dal.api.ItemDao;
import cn.choujiangba.server.dal.po.Item;
import cn.choujiangba.server.dal.po.ItemBaseStatistic;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.core.task.TaskExecutor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuiyu on 2015/10/21.
 */
public class ItemServiceTest {
    private ItemService itemService;
    private ItemDao itemDao = EasyMock.createMock(ItemDao.class);
    private ItemBaseStatisticDao itemBaseStatisticDao = EasyMock.createMock(ItemBaseStatisticDao.class);
    @Test
    public void testUpload(){

    }
    @Test
    public void testDelete() throws BizException {
        itemDao.delete((long)1);
        EasyMock.expectLastCall();
        EasyMock.replay(itemDao);
        itemService=new ItemServiceImpl().setItemDao(itemDao);
        itemService.deleteItem(1);
    }
    @Test
    public void testAddItem() throws BizException {
        Item item = new Item();
        item.setDesc_img_urls("123;");
        item.setPrice(123);
        item.setDetail("123");
        item.setThumbnail_url("123");
        item.setProperty("123;");
        item.setName("123");
        ItemBaseStatistic itemBaseStatistic = new ItemBaseStatistic();
        itemBaseStatistic.setView_num(0);
        itemBaseStatistic.setReply_num(0);
        itemBaseStatistic.setPublished_activity_num(0);
        itemBaseStatistic.setItemId(0);
        EasyMock.expect(itemDao.save(item)).andReturn(item);
        EasyMock.expect(itemBaseStatisticDao.save(itemBaseStatistic)).andReturn(itemBaseStatistic);
        EasyMock.replay(itemDao);
        EasyMock.replay(itemBaseStatisticDao);
        itemService=new ItemServiceImpl().setItemDao(itemDao).setItemBaseStatisticDao(itemBaseStatisticDao);
        List<String> properties = new LinkedList<>();
        properties.add("123");
        itemService.addItem("123", 123, properties, "123", properties, "123");

    }
    @Test
    public void testSearchByName() throws BizException {
        List<Item>items = new LinkedList<>();
         for(int i=0;i<3;i++){
            Item item = new Item();
            item.setDesc_img_urls("123;");
            item.setPrice(123);
            item.setDetail("123");
            item.setThumbnail_url("123");
            item.setProperty("123;");
            item.setName("123");
            item.setId(123);
            items.add(item);
        }
        ItemBaseStatistic baseStatistic = new ItemBaseStatistic();
        baseStatistic.setId(1);
        baseStatistic.setItemId(123);
        baseStatistic.setPublished_activity_num(123);
        baseStatistic.setReply_num(123);
        baseStatistic.setView_num(123);
        //EasyMock.expect(itemDao.findByNameLike("123")).andReturn(items);
        EasyMock.expect(itemDao.findByPropertyLike("123")).andReturn(items);
        EasyMock.expect(itemBaseStatisticDao.findByItemId(123)).andReturn(baseStatistic).anyTimes();
        EasyMock.replay(itemDao);
        EasyMock.replay(itemBaseStatisticDao);
        itemService = new ItemServiceImpl().setItemDao(itemDao).setItemBaseStatisticDao(itemBaseStatisticDao);
        List<ItemSimpleDTO> simpleDTOs = itemService.searchItemByPro("123");
        //List<ItemSimpleDTO> simpleDTOs = itemService.searchItemByName("123");
        Assert.assertNotNull(simpleDTOs);
        for(ItemSimpleDTO item :simpleDTOs)
            System.out.println(item);
    }
    @Test
    public void testGetStatistic() throws BizException {
        ItemBaseStatistic baseStatistic = new ItemBaseStatistic();
        baseStatistic.setId(1);
        baseStatistic.setItemId(123);
        baseStatistic.setPublished_activity_num(123);
        baseStatistic.setReply_num(123);
        baseStatistic.setView_num(123);
        EasyMock.expect(itemBaseStatisticDao.findByItemId(123)).andReturn(baseStatistic).anyTimes();
        EasyMock.replay(itemBaseStatisticDao);
        itemService = new ItemServiceImpl().setItemBaseStatisticDao(itemBaseStatisticDao);
        ItemStatisticDTO itemStatisticDTO = itemService.getItemStatistic(123);
        Assert.assertNotNull(itemStatisticDTO);
        System.out.print(itemStatisticDTO);
    }
    @Test
    public void testGetItem() throws BizException {
        Item item = new Item();
        item.setDesc_img_urls("123;456");
        item.setPrice(123);
        item.setDetail("123");
        item.setThumbnail_url("123");
        item.setProperty("123;456");
        item.setName("123");
        item.setId(123);
        EasyMock.expect(itemDao.findOne((long)123)).andReturn(item).anyTimes();
        EasyMock.replay(itemDao);
        itemService = new ItemServiceImpl().setItemDao(itemDao);
        ItemDetailDTO itemDetailDTO = itemService.getItem(123);
        Assert.assertNotNull(itemDetailDTO);
        System.out.print(itemDetailDTO);
    }
}

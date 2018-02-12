package cn.choujiangba.server.bal.service;

import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.dto.*;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.task.ImageHandleTask;
import cn.choujiangba.server.dal.api.*;
import cn.choujiangba.server.dal.po.*;
import org.springframework.core.task.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by shuiyu on 2015/10/21.
 */
public class ItemServiceImpl implements ItemService{

    private static Logger logger= LoggerFactory.getLogger(ItemServiceImpl.class);
    private ItemDao itemDao;
    private ItemBaseStatisticDao itemBaseStatisticDao;
    private TaskExecutor taskExecutor;
    private ItemViewStatisticDao itemViewStatisticDao;
    private ItemPurchaseStatisticDao itemPurchaseStatisticDao;
    private ItemReplyDao itemReplyDao;
    private ItemHotDao itemHotDao;
    private AccountProfileDao accountProfileDao;
    private ActivityDao activityDao;
    @Autowired
    public void setActivityDao(ActivityDao activityDao){
        this.activityDao = activityDao;
    }

    @Autowired
    public void setAccountProfileDao(AccountProfileDao accountProfileDao) {
        this.accountProfileDao = accountProfileDao;
    }

    @Autowired
    public void setItemHotDao(ItemHotDao itemHotDao) {
        this.itemHotDao = itemHotDao;
    }

    @Autowired
    public void setItemReplyDao(ItemReplyDao itemReplyDao) {
        this.itemReplyDao = itemReplyDao;
    }

    @Autowired
    public void setItemPurchaseStatisticDao(ItemPurchaseStatisticDao itemPurchaseStatisticDao) {
        this.itemPurchaseStatisticDao = itemPurchaseStatisticDao;
    }

    @Autowired
    public void setItemViewStatisticDao(ItemViewStatisticDao itemViewStatisticDao) {
        this.itemViewStatisticDao = itemViewStatisticDao;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Autowired
    public ItemServiceImpl setItemBaseStatisticDao(ItemBaseStatisticDao itemBaseStatisticDao) {
        this.itemBaseStatisticDao = itemBaseStatisticDao;
        return this;
    }

    @Autowired
    public ItemServiceImpl setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
        return this;
    }

    @Override
    public String uploadImg(long itemId, InputStream file,long length) throws BizException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName=sdf.format(new Date())+"_"+String.valueOf(new Random().nextInt(899) + 100)+".jpg";
        taskExecutor.execute(new ImageHandleTask(
                fileName,file,length));
        return fileName;
    }

    @Override
    public void deleteItem(long itemId) throws BizException {
        if(itemId<1)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"userId is not valid");
        logger.info("delete item id ="+itemId);
        //当商品和活动还有联系的时候商品不能删除
        // todo
        itemDao.delete(itemId);
        logger.info("successfully delete id=" + itemId);
    }

    @Override
    public void addItem(String name, int price, List<String> properties, String thumbnailUrl, List<String> imgUrls, String detail) throws BizException {
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(thumbnailUrl)||StringUtils.isEmpty(detail)||properties==null||imgUrls==null)
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"parameters should not be null");
        logger.info(String.format("add item name=%s,price=%d,thumbnailUrl=%s,detail=%s",name,price,thumbnailUrl,detail));
        String property ="";
        String imgUrl="";
        for(String item:properties) {
            logger.info("property" + item);
            property=property+item+";";
        }
        for(String url:imgUrls) {
            logger.info("imgUrl" + url);
            imgUrl=imgUrl+url+";";
        }
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setProperty(property);
        item.setThumbnail_url(thumbnailUrl);
        item.setDesc_img_urls(imgUrl);
        item.setDetail(detail);
        itemDao.save(item);
        ItemBaseStatistic itemBaseStatistic = new ItemBaseStatistic();
        itemBaseStatistic.setView_num(0);
        itemBaseStatistic.setReply_num(0);
        itemBaseStatistic.setPublished_activity_num(0);
        itemBaseStatistic.setItemId(item.getId());
        itemBaseStatisticDao.save(itemBaseStatistic);
        System.out.println(itemBaseStatistic.getId());
    }

    @Override
    public Pageable<ItemSimpleDTO> listItem(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<Item> items = itemDao.findAll(new PageRequest(page - 1, count, sort));
        Pageable<ItemSimpleDTO> itemSimple = new Pageable<>();

        itemSimple.setCurrentPage(page);
        itemSimple.setHasPrePage(items.hasPrevious());
        itemSimple.setHasNextPage(items.hasNext());

        for(Item item:items){
            ItemSimpleDTO itemSimpleDTO  = new ItemSimpleDTO();
            itemSimpleDTO.setItemId(item.getId());
            itemSimpleDTO.setName(item.getName());
            itemSimpleDTO.setThumbnailUrl(item.getThumbnail_url());
            itemSimpleDTO.setActivityNum(itemBaseStatisticDao.findByItemId(item.getId()).getPublished_activity_num());
            itemSimple.getContent().add(itemSimpleDTO);
        }
        logger.info(String.format("get item list page=%d count=%d",page,count)+itemSimple.toString());
        return itemSimple;
    }

    @Override
    public List<ItemSimpleDTO> searchItemByName(String keyword) throws BizException {
        if(StringUtils.isEmpty(keyword))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"keyword is null");
        logger.info(String.format("search item as keyword=%s", keyword));
        List<Item>items = itemDao.findByNameLike(keyword);
        List<ItemSimpleDTO> results=new LinkedList<>();
        for(Item item:items){
            ItemSimpleDTO itemSimpleDTO  = new ItemSimpleDTO();
            itemSimpleDTO.setItemId(item.getId());
            itemSimpleDTO.setName(item.getName());
            itemSimpleDTO.setThumbnailUrl(item.getThumbnail_url());
            itemSimpleDTO.setActivityNum(itemBaseStatisticDao.findByItemId(item.getId()).getPublished_activity_num());
            results.add(itemSimpleDTO);
        }
        return results;
    }

    @Override
    public List<ItemSimpleDTO> searchItemByPro(String keyword) throws BizException {
        if(StringUtils.isEmpty(keyword))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"keyword is null");
        logger.info(String.format("search item as keyword=%s",keyword));
        List<Item>items = itemDao.findByPropertyLike(keyword);
        List<ItemSimpleDTO> results=new LinkedList<>();
        for(Item item:items){
            ItemSimpleDTO itemSimpleDTO  = new ItemSimpleDTO();
            itemSimpleDTO.setItemId(item.getId());
            itemSimpleDTO.setName(item.getName());
            itemSimpleDTO.setThumbnailUrl(item.getThumbnail_url());
            itemSimpleDTO.setActivityNum(itemBaseStatisticDao.findByItemId(item.getId()).getPublished_activity_num());
            results.add(itemSimpleDTO);
        }
        return results;
    }

    @Override
    public ItemStatisticDTO getItemStatistic(long itemId) throws BizException {
        if(itemId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("itemId=%d is not valid",itemId));
        logger.info(String.format("get itemId=%d 's baseStatistic",itemId));
        ItemBaseStatistic baseStatistic  =  itemBaseStatisticDao.findByItemId(itemId);
        ItemStatisticDTO statisticDTO = new ItemStatisticDTO();
        statisticDTO.setActivityNum(baseStatistic.getPublished_activity_num());
        statisticDTO.setReplyNum(baseStatistic.getReply_num());
        statisticDTO.setViewNum(baseStatistic.getView_num());
        return statisticDTO;
    }

    @Override
    public ItemDetailDTO getItem(long itemId) throws BizException {
        if(itemId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("itemId=%d is not valid",itemId));
        Item item = itemDao.findOne(itemId);
        if(item==null)
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,String.format("there is no such item match itemId=%d",itemId));
        ItemDetailDTO detailDTO = new ItemDetailDTO();
        detailDTO.setName(item.getName());
        detailDTO.setThumbnailUrl(item.getThumbnail_url());
        detailDTO.setDetail(item.getDetail());
        detailDTO.setItemId(item.getId());
        detailDTO.setPrice(item.getPrice());
        String[] properties = item.getProperty().split(";");
        String[] imgUrls = item.getDesc_img_urls().split(";");
        for(String property:properties)
            detailDTO.getProperty().add(property);
        for(String imgUrl:imgUrls)
            detailDTO.getDescImgUrl().add(imgUrl);
        return detailDTO;
    }

    @Override
    public boolean addItemReply(long itemId, long activityId, long uid, String comment, List<String> urls) throws BizException {
        if(StringUtils.isEmpty(comment))
            throw new BizException(BizException.ERROR_CODE_INSTANCE_NOT_FOUND,"parameter should not be null");
        if(itemId<1||activityId<1||uid<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id is not valid");
        logger.info(String.format("uid=%d add comment=%s to activityId=%d itemId=%d",uid,comment,activityId,itemId));
        Activity activity =activityDao.findOne(activityId);
        if(uid!=activity.getWinnerId())
            throw new BizException(BizException.ERROR_CODE_NOT_CORRECT_WINNER,String.format("uid=%d is not activityId=%d 's winner",uid,activityId));
        ItemReply reply = new ItemReply();
        reply.setActivityId(activityId);
        reply.setCreate_at(new Date());
        reply.setItemId(itemId);
        reply.setUid(uid);
        reply.setText_content(comment);
        String url = "";
        for(String item:urls)
            url = url+item+";";
        reply.setImg_urls(url);
        try{
            itemReplyDao.save(reply);
            itemBaseStatisticDao.updateReplyNum(itemId);
            return true;
        }catch (Exception e){
            logger.info("Failed to add comment"+e.getMessage());
            return false;
        }
    }

    @Override
    public Pageable<ItemViewStatisticDTO> listAllViewStatistic(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ItemViewStatistic> items = itemViewStatisticDao.findAll(new PageRequest(page-1,count,sort));
        Pageable<ItemViewStatisticDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasPrePage(items.hasPrevious());
        result.setHasNextPage(items.hasNext());
        logger.info(String.format("list view statistic page=%d,count=%d", page, count));
        for(ItemViewStatistic item:items){
            ItemViewStatisticDTO itemViewStatisticDTO = new ItemViewStatisticDTO();
            itemViewStatisticDTO.setUid(item.getUid());
            itemViewStatisticDTO.setCity(item.getCity());
            itemViewStatisticDTO.setDate(item.getView_time());
            itemViewStatisticDTO.setDistrict(item.getDistrict());
            itemViewStatisticDTO.setId(item.getId());
            itemViewStatisticDTO.setIp(item.getIp());
            itemViewStatisticDTO.setItemId(item.getItem_id());
            itemViewStatisticDTO.setLatitude(item.getLatitiude());
            itemViewStatisticDTO.setLongitude(item.getLongitude());
            result.getContent().add(itemViewStatisticDTO);
        }
        logger.info("successfully get view statistic" + result.toString());
        return result;
    }

    @Override
    public Pageable<ItemPurchaseStatisticDTO> listAllPurchaseStatistic(int page, int count) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ItemPurchaseStatistic> items = itemPurchaseStatisticDao.findAll(new PageRequest(page-1,count,sort));
        Pageable<ItemPurchaseStatisticDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasPrePage(items.hasPrevious());
        result.setHasNextPage(items.hasNext());
        logger.info(String.format("list purchase statistic page=%d,count=%d",page,count));
        for(ItemPurchaseStatistic item:items){
            ItemPurchaseStatisticDTO itemPurchaseStatisticDTO = new ItemPurchaseStatisticDTO();
            itemPurchaseStatisticDTO.setItemId(item.getItem_id());
            itemPurchaseStatisticDTO.setId(item.getId());
            itemPurchaseStatisticDTO.setDate(item.getBuy_time());
            itemPurchaseStatisticDTO.setPay(item.getPay());
            itemPurchaseStatisticDTO.setUid(item.getUid());
            result.getContent().add(itemPurchaseStatisticDTO);
        }
        logger.info("successfully get view statistic"+result.toString());
        return result;
    }

    @Override
    public Pageable<ItemReplyDTO> listReplyByItemId(int page, int count, long itemId) throws BizException {
        if(page<1||count<1||count>50)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("page=%d,count=%d is not valid",page,count));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ItemReply> items = itemReplyDao.findByItemId(itemId, new PageRequest(page - 1, count, sort));
        Pageable<ItemReplyDTO> result = new Pageable<>();

        result.setCurrentPage(page);
        result.setHasPrePage(items.hasPrevious());
        result.setHasNextPage(items.hasNext());
        logger.info(String.format("list itemId=%d comment  page=%d,count=%d",itemId,page,count));
        for(ItemReply item:items){
            ItemReplyDTO itemReplyDTO = new ItemReplyDTO();
            itemReplyDTO.setUid(item.getUid());
            itemReplyDTO.setDate(item.getCreate_at());
            itemReplyDTO.setComment(item.getText_content());
            if(item.getImg_urls()!=null) {
                String[] urls = item.getImg_urls().split(";");
                for(String url:urls)
                    itemReplyDTO.getUrls().add(url);
            }
            result.getContent().add(itemReplyDTO);
        }
        logger.info("successfully get activity comment"+result.toString());
        return result;
    }

    @Override
    public List<ItemReplyDTO> listReply(long itemId) throws BizException {
        if(itemId < 1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id is not valid");

        List<ItemReply> items = itemReplyDao.findByItemId(itemId);

        List<ItemReplyDTO> list = new LinkedList<>();
        ItemReplyDTO itemReplyDTO = null;
        for(ItemReply itemReply : items){
            itemReplyDTO = new ItemReplyDTO();
            itemReplyDTO.setUid(itemReply.getUid());

            Item item = itemDao.findOne(itemId);
            itemReplyDTO.setItemName(item.getName());

            AccountProfile accountProfile = accountProfileDao.findByUid(itemReply.getUid());
            itemReplyDTO.setNickName(accountProfile.getNickname());
            itemReplyDTO.setAvatarUrl(accountProfile.getAvatar_url());

            itemReplyDTO.setItemId(itemReply.getItemId());
            itemReplyDTO.setActivityId(itemReply.getActivityId());
            itemReplyDTO.setDate(itemReply.getCreate_at());
            itemReplyDTO.setComment(itemReply.getText_content());
            if(itemReply.getImg_urls()!=null) {
                String[] urls = itemReply.getImg_urls().split(";");
                for(String url:urls)
                    itemReplyDTO.getUrls().add(url);
            }

            list.add(itemReplyDTO);
        }

        return list;
    }

    @Override
    public ItemReplyDTO listReplyByActivityId(long activityId) throws BizException {
        if(activityId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,String.format("id=%d is not valid",activityId));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        ItemReply item = itemReplyDao.findByActivityId(activityId);
        logger.info(String.format("list itemId=%d comment  ", activityId) + item.toString());
        ItemReplyDTO itemReplyDTO = new ItemReplyDTO();
        itemReplyDTO.setUid(item.getUid());
        itemReplyDTO.setDate(item.getCreate_at());
        itemReplyDTO.setComment(item.getText_content());
        if(item.getImg_urls()!=null) {
            String[] urls = item.getImg_urls().split(";");
            for(String url:urls)
                itemReplyDTO.getUrls().add(url);
        }
        logger.info("successfully get activity comment" + itemReplyDTO.toString());
        return itemReplyDTO;
    }

    @Override
    public boolean addHotItem(long itemId, String banner) throws BizException {
        if(StringUtils.isEmpty(banner))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"parameters should not be null");
        if(itemId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id is not valid");
        ItemHot hot = new ItemHot();
        hot.setItemId(itemId);
        hot.setBannerUrl(banner);
        try {
            itemHotDao.save(hot);
            return true;
        }catch (Exception e){
            logger.info("failed to save hot item"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteHotItem(long hotId) throws BizException {
        if(hotId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id is not valid");
        try{
            itemHotDao.delete(hotId);
            return true;
        }catch (Exception e)
        {
            logger.info("failed to delete hot item"+e.getMessage());
            return false;
        }
    }

    @Override
    public List<HotItemDTO> listAllHot() throws BizException {
        List<HotItemDTO> hots = new LinkedList<>();
        List<ItemHot>items = itemHotDao.findAll();
        logger.info("get all hot item");
        for(ItemHot item:items){
            HotItemDTO hot = new HotItemDTO();
            Item good = itemDao.findOne(item.getItemId());
            hot.setItemName(good.getName());
            hot.setItemId(item.getItemId());
            hot.setBanner(item.getBannerUrl());
            hot.setId(item.getId());
            hots.add(hot);
        }

        return hots;
    }

    @Override
    public boolean addItemViewRecord(long uid,long itemId, String city, String district, double longitude, double latitude, String ip) throws BizException {
        if(uid<1||itemId<1)
            throw new BizException(BizException.ERROR_CODE_PARAMETER_NOT_VALID,"id not valid");
        if(StringUtils.isEmpty(ip))
            throw new BizException(BizException.ERROR_CODE_FIELD_NOT_NULL,"parameter should not be null");
        ItemViewStatistic viewStatistic = new ItemViewStatistic();
        viewStatistic.setUid(uid);
        viewStatistic.setDistrict(district);
        viewStatistic.setCity(city);
        viewStatistic.setIp(ip);
        viewStatistic.setLatitiude(latitude);
        viewStatistic.setLongitude(longitude);
        viewStatistic.setView_time(new Date().getTime());
        viewStatistic.setItem_id(itemId);
        try {
            itemViewStatisticDao.save(viewStatistic);
            itemBaseStatisticDao.updateViewNum(itemId);
        }catch (Exception e){
            logger.info("failed to add view Record"+e.getMessage());
            return  false;
        }
        return true;
    }

}

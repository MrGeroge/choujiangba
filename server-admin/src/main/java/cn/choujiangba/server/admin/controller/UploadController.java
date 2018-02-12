package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.exception.BizException;
import cn.choujiangba.server.bal.file.FileClient;
import cn.choujiangba.server.bal.file.OssFileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hao on 2015/10/21.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final static Logger logger= LoggerFactory.getLogger(UploadController.class);

    private ItemService itemService;

    @Autowired
    public UploadController setItemService(ItemService itemService){
        this.itemService = itemService;
        return this;
    }

    @RequestMapping(value = "/imgs")//method = RequestMethod.POST)
    public List<String> upload(
            @RequestParam(value = "files",required = true) MultipartFile[] files)
            throws IOException,BizException{
        List<String> list = new LinkedList<>();

        FileClient fileClient=new OssFileClient();

        for(MultipartFile file : files){
            if (!file.isEmpty()) {
                int dot =file.getOriginalFilename().lastIndexOf('.');
                if ((dot >-1) && (dot < (file.getOriginalFilename().length() - 1))) {
                    String fileExt= file.getOriginalFilename().substring(dot + 1);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileName=sdf.format(new Date())+"_"+String.valueOf(new Random().nextInt(899) + 100)+"."+fileExt;
                    //将字节流转换为输入流
                    InputStream inputStream = new ByteArrayInputStream(file.getBytes());

                    String url=fileClient.save(fileName,inputStream,file.getSize());
                    list.add(url);

                    logger.info("upload image file,info url="+url);
                }
            }
        }

        return list;
    }

    @RequestMapping(value = "/img")//method = RequestMethod.POST)
    public Map<String,String> uploadImg(
            @RequestParam(value = "file",required = true) MultipartFile file)
            throws IOException,BizException{
        Map<String,String> result = new HashMap<>();

        FileClient fileClient=new OssFileClient();

        if (!file.isEmpty()) {
            int dot =file.getOriginalFilename().lastIndexOf('.');
            if ((dot >-1) && (dot < (file.getOriginalFilename().length() - 1))) {
                String fileExt= file.getOriginalFilename().substring(dot + 1);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName=sdf.format(new Date())+"_"+String.valueOf(new Random().nextInt(899) + 100)+"."+fileExt;
                //将字节流转换为输入流
                InputStream inputStream = new ByteArrayInputStream(file.getBytes());

                String url=fileClient.save(fileName,inputStream,file.getSize());

                result.put("link",url);

                logger.info("upload image file,info url="+url);
            }
        }

        return result;
    }
}

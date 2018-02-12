package cn.choujiangba.server.bal.task;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
/**
 * Author:zhangyu
 * create on 15/9/29.
 */
public class ImageHandleTask implements Runnable{

    private static final Logger logger= LoggerFactory.getLogger(ImageHandleTask.class);

    private static final String accessKey = "YXQgY83rQThKc7jb";
    private static final String secretKey = "nzSfm46kUVqLnCNPt9L0w8xzWB2eyG";
    private static final String bucketName="wbsn2";
    private static final String endpoint="http://oss-cn-hangzhou.aliyuncs.com";

    private String fileKey;
    private InputStream inputStream;
    private long length;
    public ImageHandleTask(String fileKey,InputStream inputStream,long length) {
        this.fileKey=fileKey;
        this.inputStream = inputStream;
        this.length = length;
    }

    @Override
    public void run() {

            //初始化OSSClient
            OSSClient client = new OSSClient(endpoint, accessKey, secretKey );

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(length);
            // 上传Object.
            PutObjectResult result = client.putObject(bucketName,fileKey, inputStream, meta);

            //删除本地临时文件
            if(callBack!=null){
                callBack.onSuccess();
            }

    }

    public interface HandleCallBack{
        void onSuccess();
    }

    private HandleCallBack callBack;

    public ImageHandleTask setCallBack(HandleCallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}

package cn.choujiangba.server.bal.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author:zhangyu
 * create on 15/10/22.
 */
public class OssFileClient implements FileClient {

    private static final String accessKey = "q0Yqn3poBscRmNPd";
    private static final String secretKey = "agh3svAOvb0dexrBEbr3aXIT0Ua6cQ";
    private static final String bucketName="choujiangba";

    private static final String endpoint="http://oss-cn-hangzhou.aliyuncs.com";

    private static final Logger logger= LoggerFactory.getLogger(OssFileClient.class);

    @Override
    public String save(String fileKey, InputStream inStream, long size) throws IOException {

        OSSClient client = new OSSClient(endpoint, accessKey, secretKey );
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(size);
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName,fileKey,inStream , meta);

        return "http://"+bucketName+".oss-cn-hangzhou.aliyuncs.com/"+fileKey;
    }

    @Override
    public String asyncSave(String fileKey, InputStream inStream, long size, SaveCallBack callBack) {
        return null;
    }
}

package com.enstudy.demo.util;

import com.enstudy.demo.config.QiNiuConfig;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageUploadUtil {
    @Autowired
    private QiNiuConfig qiNiuConfig;
    /**
     * 获取前端上传的Token
     * @param fileName
     * @return
     */
    public String getToken(String fileName) {
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        String upToken;
        //upToken = auth.uploadToken(qiNiuConfig.getBucket());//简单上传凭证,这种凭证上传同名文件会失败
        upToken = auth.uploadToken(qiNiuConfig.getBucket(), fileName);//覆盖上传凭证
        return upToken;
    }

    /**
     * 上传MultipartFile 到七牛云服务器
     * @param dir: 七牛云bucket下的目录
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadImageToQiNiu(String dir, MultipartFile file) {
        String filename = file.getOriginalFilename();
        String fullName = dir + filename;
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //把文件转化为字节数组
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = file.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = is.read(b)) != -1){
                bos.write(b, 0, len);
            }
            byte[] uploadBytes= bos.toByteArray();
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            String upToken;
            //upToken = auth.uploadToken(qiNiuConfig.getBucket());//简单上传凭证,这种凭证上传同名文件会失败
            upToken = auth.uploadToken(qiNiuConfig.getBucket(), fullName);//覆盖上传凭证,带文件名的上传凭证,同名文件会覆盖
            //默认上传接口回复对象
            DefaultPutRet putRet;
            //进行上传操作，传入文件的字节数组，文件名，上传空间，得到回复对象
            Response response = uploadManager.put(uploadBytes, fullName, upToken);
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);//key 文件名
            System.out.println(putRet.hash);//hash 七牛返回的文件存储的地址，可以使用这个地址加七牛给你提供的前缀访问到这个视频。
            return qiNiuConfig.getEndpoint()+qiNiuConfig.getDomainOfBucket()+"/"+fullName;
        }catch (QiniuException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

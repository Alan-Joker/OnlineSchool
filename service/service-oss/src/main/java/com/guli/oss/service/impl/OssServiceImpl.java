package com.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.guli.oss.service.OssService;
import com.guli.oss.utils.ConstantPropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Alan_
 * @create 2021/1/3 22:14
 */
@Service
public class OssServiceImpl implements OssService {

    private static String[] TYPESTR = {"png","jpg","bmp","gif","jpeg"};
    /**
     * 上传头像到oss
     *
     * @param file
     * @return
     */
    @Override
    public String uploadFileAuatar(MultipartFile file) {

        String endPoind = ConstantPropertiesUtils.END_POIND;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String accessKeyId1 = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret1 = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        String endpoint = endPoind;
        String accessKeyId = accessKeyId1;
        String accessKeySecret = accessKeySecret1;


        // 创建PutObjectRequest对象。
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //判断文件格式合法性
           /* for (String type : TYPESTR) {
                //判断文件名是否以 TYPESTR中的格式结尾
                if(!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    return "格式不正确";
                }
            }
*/
            //判断文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                return "文件内容不正确";
            }
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = new DateTime().toString("yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replaceAll("-","") +ext;
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, inputStream);
            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            //拼接文件路径 https://edugulionline.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://" + bucketName + "." + endPoind + "/" + filename;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

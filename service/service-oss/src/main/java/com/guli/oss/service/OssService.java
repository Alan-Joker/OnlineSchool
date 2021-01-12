package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Alan_
 * @create 2021/1/3 22:13
 */
public interface OssService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadFileAuatar(MultipartFile file);
}

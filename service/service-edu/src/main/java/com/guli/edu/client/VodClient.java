package com.guli.edu.client;

import com.guli.base.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Alan_
 * @create 2021/1/31 12:47
 */
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    Result removeAlyVideo(@PathVariable("id") String id);
}

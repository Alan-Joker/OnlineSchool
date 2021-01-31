package com.guli.edu.client;

import com.guli.base.entity.Result;
import org.springframework.stereotype.Component;

/**
 * @Author Alan_
 * @create 2021/1/31 14:37
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().data("error","timeout");
    }
}

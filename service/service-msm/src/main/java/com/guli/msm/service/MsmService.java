package com.guli.msm.service;

import java.util.Map;

/**
 * @Author Alan_
 * @create 2021/2/7 20:44
 */
public interface MsmService {
    boolean send(Map<String, Object> map, String phone);
}

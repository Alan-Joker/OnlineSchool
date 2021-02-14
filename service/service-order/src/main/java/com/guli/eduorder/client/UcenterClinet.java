package com.guli.eduorder.client;

import com.guli.base.entity.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author Alan_
 * @create 2021/2/14 18:56
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClinet {
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
     UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}

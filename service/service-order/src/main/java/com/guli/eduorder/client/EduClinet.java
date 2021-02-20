package com.guli.eduorder.client;

import com.guli.base.entity.ordervo.CoursePublishVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author Alan_
 * @create 2021/2/14 18:56
 */
@Component
@FeignClient("service-edu")
public interface EduClinet {

    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
     CoursePublishVo getCourseInfoOrder(@PathVariable("id") String id);
}

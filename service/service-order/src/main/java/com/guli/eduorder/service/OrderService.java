package com.guli.eduorder.service;

import com.guli.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-02-14
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String id);
}

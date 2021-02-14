package com.guli.eduorder.service.impl;

import com.guli.base.entity.Result;
import com.guli.base.entity.ordervo.CoursePublishVo;
import com.guli.base.entity.ordervo.UcenterMemberOrder;
import com.guli.eduorder.client.EduClinet;
import com.guli.eduorder.client.UcenterClinet;
import com.guli.eduorder.entity.Order;
import com.guli.eduorder.mapper.OrderMapper;
import com.guli.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-02-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClinet eduClinet;

    @Autowired
    private UcenterClinet ucenterClinet;

    @Override
    public String createOrders(String courseId, String id) {

        //远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClinet.getUserInfoOrder(id);
        //通过远程调用根据课程id获取课程信息
        CoursePublishVo courseInfoOrder = eduClinet.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(id);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1

        baseMapper.insert(order);

        //返回订单号
        return order.getOrderNo();
    }
}

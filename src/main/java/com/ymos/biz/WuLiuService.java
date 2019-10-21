package com.ymos.biz;

import com.ymos.entity.Order;

import java.util.List;

public interface WuLiuService extends BaseService<Order> {

    List<Order> AllCount();

    int update(Order order);

}

package com.ymos.mapper;

import com.ymos.entity.Order;

import java.util.List;

public interface WuLiuMapper extends BaseMapper<Order>{

    List<Order> AllCount();

    int update(Order order);

}

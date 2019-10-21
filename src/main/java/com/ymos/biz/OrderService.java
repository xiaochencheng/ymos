package com.ymos.biz;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<OrderReport> exportExcel(OrderReport orderReport);

    int create(List<Order> orders);





}

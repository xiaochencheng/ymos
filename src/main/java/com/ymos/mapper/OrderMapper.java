package com.ymos.mapper;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import com.ymos.entity.Sku;
import com.ymos.entity.SkuReport;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<OrderReport> exportExcel(OrderReport orderReport);

    int create(List<Order> orders);



}

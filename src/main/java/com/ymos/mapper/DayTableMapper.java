package com.ymos.mapper;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;

import java.util.List;

public interface DayTableMapper extends BaseMapper<Order> {

    List<OrderReport> exportExcel(OrderReport orderReport);
}

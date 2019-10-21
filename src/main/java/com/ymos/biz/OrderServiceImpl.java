package com.ymos.biz;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import com.ymos.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl  extends BaseServiceImpl<Order> implements OrderService {

    OrderMapper orderMapper;

    @Resource
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
        this.mapper=orderMapper;
    }

    @Override
    public List<OrderReport> exportExcel(OrderReport orderReport) {
        List<OrderReport> list=orderMapper.exportExcel(orderReport);
        return list;
    }

    @Override
    public int create(List<Order> orders) {
        int count=orderMapper.create(orders);
        return count;
    }


}

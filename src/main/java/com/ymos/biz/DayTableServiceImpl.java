package com.ymos.biz;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import com.ymos.mapper.DayTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dayTableService")
public class DayTableServiceImpl extends BaseServiceImpl<Order> implements DayTableService {

    DayTableMapper dayTableMapper;

    @Resource
    public void setDayTableMapper(DayTableMapper dayTableMapper) {
        this.dayTableMapper = dayTableMapper;
        this.mapper=dayTableMapper;
    }

    @Override
    public List<OrderReport> exportExcel(OrderReport orderReport) {
        List<OrderReport> list=dayTableMapper.exportExcel(orderReport);
        return list;
    }
}

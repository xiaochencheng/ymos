package com.ymos.biz;

import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import com.ymos.mapper.WuliuInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("wuliuInfoService")
public class WuliuInfoServiceImpl extends BaseServiceImpl<Order>  implements WuliuInfoService {

   WuliuInfoMapper wuliuInfoMapper;

    @Resource
    public void setWuliuInfoMapper(WuliuInfoMapper wuliuInfoMapper) {
        this.wuliuInfoMapper = wuliuInfoMapper;
        this.mapper=wuliuInfoMapper;
    }

    @Override
    public List<OrderReport> exportExcel(OrderReport orderReport) {
        List<OrderReport> list=wuliuInfoMapper.exportExcel(orderReport);
        return list;
    }
}

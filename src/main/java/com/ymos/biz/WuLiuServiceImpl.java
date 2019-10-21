package com.ymos.biz;

import com.ymos.entity.Order;
import com.ymos.mapper.WuLiuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("wuLiuService")
public class WuLiuServiceImpl extends BaseServiceImpl<Order>  implements WuLiuService{

     WuLiuMapper wuLiuMapper;

    @Resource
    public void setWuLiuMapper(WuLiuMapper wuLiuMapper) {
        this.wuLiuMapper = wuLiuMapper;
        this.mapper=wuLiuMapper;
    }


    @Override
    public List<Order> AllCount() {
        List<Order> list=wuLiuMapper.AllCount();
        return list;
    }

    @Override
    public int update(Order order){
        int count=wuLiuMapper.update(order);
        return count;
    }

}

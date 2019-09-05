package com.ymos.biz;

import com.ymos.entity.SkuList;
import com.ymos.mapper.SkuListMapper;
import com.ymos.biz.BaseService;
import com.ymos.biz.SkuListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("skuListService")
public class SkuListServiceImpl extends BaseServiceImpl<SkuList> implements SkuListService {

    SkuListMapper skuListMapper;

    @Resource
    public void setSkuListMapper(SkuListMapper skuListMapper) {
        this.skuListMapper = skuListMapper;
        this.mapper=skuListMapper;
    }

    @Override
    public int create(SkuList skuList) {
        int count=skuListMapper.create(skuList);
        return count;
    }

    @Override
    public void modify(SkuList skuList) {
        skuListMapper.update(skuList);
    }
}

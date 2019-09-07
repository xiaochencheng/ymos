package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.Sku;
import com.ymos.entity.SkuList;
import com.ymos.entity.SkuReport;
import com.ymos.mapper.SkuMapper;
import com.ymos.biz.SkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("skuService")
public class SkuServiceImpl extends  BaseServiceImpl<Sku> implements SkuService {

    SkuMapper skuMapper;

    @Resource
    public void setSkuMapper(SkuMapper skuMapper) {
        this.skuMapper = skuMapper;
        this.mapper=skuMapper;
    }

    @Override
    public List<Product> getFindSPU() {
        List<Product> list=skuMapper.getFindSPU();
        return list;
    }

    @Override
    public int create(Sku sku) {
        int count=skuMapper.create(sku);
        return count;
    }

    @Override
    public List<Product> getSpuInfo(String spu) {
        List<Product> list=skuMapper.getSpuInfo(spu);
        return list;
    }

    @Override
    public List<SkuReport> exportExcel(SkuReport skuReport) {
        return skuMapper.exportExcel(skuReport);
    }

    @Override
    public int findSkunOne(String sku) {
            int  result=  skuMapper.findSkunOne(sku);
        return result;
    }


}

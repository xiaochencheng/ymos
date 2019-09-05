package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.Sku;
import com.ymos.entity.SkuReport;

import java.util.List;

public interface SkuService extends BaseService<Sku> {

    List<Product> getFindSPU();

    int create(Sku sku);

    List<Product> getSpuInfo(String spu);

    List<SkuReport> exportExcel(SkuReport skuReport);



}

package com.ymos.mapper;

import com.ymos.entity.Product;
import com.ymos.entity.ReviewReport;
import com.ymos.entity.Sku;
import com.ymos.entity.SkuReport;

import java.util.List;

public interface SkuMapper extends BaseMapper<Sku> {

  List<Product> getFindSPU();

    int create(Sku sku);

    List<Product> getSpuInfo(String spu);

  List<SkuReport> exportExcel(SkuReport skuReport);


}

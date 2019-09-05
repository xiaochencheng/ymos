package com.ymos.mapper;

import com.ymos.entity.Sku;
import com.ymos.entity.SkuList;

public interface SkuListMapper extends BaseMapper<SkuList> {

    int create(SkuList skuList);

    int update(SkuList skuList);

}

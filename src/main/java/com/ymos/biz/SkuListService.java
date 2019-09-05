package com.ymos.biz;

import com.ymos.entity.SkuList;

public interface SkuListService extends BaseService<SkuList> {
    int create(SkuList skuList);

    void modify(SkuList skuList);

}

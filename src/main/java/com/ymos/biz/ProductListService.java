package com.ymos.biz;

import com.ymos.entity.ProductList;

import java.util.List;

public interface ProductListService extends BaseService<ProductList> {

    List<ProductList> proList();

}

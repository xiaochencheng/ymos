package com.ymos.mapper;

import com.ymos.entity.ProductList;

import java.util.List;

public interface ProductListMapper extends BaseMapper<ProductList> {

    List<ProductList> proList();


}

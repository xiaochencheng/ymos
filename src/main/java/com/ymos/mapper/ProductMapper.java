package com.ymos.mapper;

import com.ymos.entity.Product;
import com.ymos.entity.ProductList;
import com.ymos.entity.ProductReport;
import com.ymos.entity.ReviewReport;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    int queryMaxId();

    int create(Product product);

    List<Product> getFindList();

    List<ProductReport> queryExcelData(ProductReport productReport);

}

package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.ProductReport;

import java.util.List;

public interface ProductService extends BaseService<Product> {

    int queryMaxId();

    int create(Product product);

    void modify(Product product);

    List<Product> getFindList();

    List<ProductReport> queryExcelData(ProductReport productReport);

}

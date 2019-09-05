package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.ProductList;
import com.ymos.entity.ProductReport;
import com.ymos.mapper.ProductMapper;
import com.ymos.biz.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Component
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {


    ProductMapper productMapper;

    @Resource
    private  void setProductMapper(ProductMapper productMapper){
        this.mapper=productMapper;
        this.productMapper=productMapper;
    }

    @Override
    public int queryMaxId() {
        return productMapper.queryMaxId();
    }

    @Override
    public int create(Product product) {
        return productMapper.create(product);
    }

    @Override
    public void modify(Product product) {
        productMapper.update(product);
    }

    @Override
    public List<Product> getFindList() {
        List<Product> list=productMapper.getFindList();
        return list;
    }

    @Override
    public List<ProductReport> queryExcelData(ProductReport productReport) {
        return productMapper.queryExcelData(productReport);
    }

}

package com.ymos.biz;

import com.ymos.entity.ProductList;
import com.ymos.mapper.ProductListMapper;
import com.ymos.biz.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Component
@Service("productListService")
public class ProductListServiceImpl extends BaseServiceImpl<ProductList> implements ProductListService {

    ProductListMapper productListMapper;

    @Resource
    public void setProductListMapper(ProductListMapper productListMapper) {
        this.mapper=productListMapper;
        this.productListMapper=productListMapper;
    }


    @Override
    public List<ProductList> proList() {
        List<ProductList> lists=productListMapper.proList();
        return lists;
    }
}

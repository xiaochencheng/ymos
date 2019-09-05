package com.ymos.controller;

import com.ymos.entity.ProductList;
import com.ymos.entity.ProductListForm;
import com.ymos.entity.ProductQuery;
import com.ymos.biz.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/proList")
public class ProductListController  extends CUDController<ProductList, ProductQuery, ProductListForm,ProductListService> {


   private ProductListService productListService;

    public ProductListController() {
        super("proList");
    }

    @Autowired
    public void setProductListService(ProductListService productListService) {
        this.productListService = productListService;
        this.service=productListService;

    }

    //public ProductListService getProductListService() {
    //    return productListService;
    //}

    @ResponseBody
    @RequestMapping(value = "/queryName")
    public List<ProductList> queryProudList(){
        try {
            List<ProductList> list=productListService.proList();
            return productListService.proList();
        }catch (Exception e){
            return null;
        }

    }


    @Override
    protected void innerSave(ProductListForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }
}

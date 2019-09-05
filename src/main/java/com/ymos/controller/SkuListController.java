package com.ymos.controller;

import com.ymos.entity.*;
import com.ymos.biz.SkuListService;
import com.ymos.biz.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/skulist")
public class SkuListController extends CUDController<SkuList, SkuListQuery, SkuListForm, SkuListService>   {

    SkuListService skuListService;

    //SkuService skuService;

    @Autowired
    public void setSkuListService(SkuListService skuListService) {
        this.skuListService = skuListService;
        this.service=skuListService;
    }

    public SkuListController() {
        super("skulist");
    }


    //protected String innerList(int page,SkuQuery query,Model model,HttpServletRequest request,HttpServletResponse response){
    //
    //    Paginator paginator=this.intiPaginator(page,query);
    //    if(query.getSize()!=null){
    //        paginator.setSize(query.getSize());
    //    }
    //    List<Sku> datas= Collections.emptyList();
    //    boolean pre=this.preList(query,paginator,model,request,response);
    //    if(pre){
    //        datas=this.service.select(paginator);
    //        paginator=this.service.initPage(paginator,datas);
    //    }
    //    model.addAttribute("query",query);
    //    model.addAttribute("datas",datas);
    //    model.addAttribute("paginator",paginator);
    //    model.addAttribute("hasDatas",Boolean.valueOf(datas!=null && !datas.isEmpty()));
    //    this.afterList(query,paginator,model,request,response);
    //    //return "sku/skulist";
    //    return "skulist/list";
    //}

    /**
     * 修改产品数据
     */
    //@ResponseBody
    //@RequestMapping(value = "/review", method = RequestMethod.POST)
    //public Result<SkuList> review(HttpServletRequest request, HttpServletResponse response, SkuList skuList) {
    //
    //    skuListService.modify(skuList);
    //
    //    return new Result<SkuList>().setData(skuList).setFlag(true);
    //
    //}


    @Override
    protected boolean preList(SkuListQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

    @Override
    protected void innerSave(SkuListForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }
}

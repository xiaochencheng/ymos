package com.ymos.controller;


import com.ymos.entity.Id;
import com.ymos.entity.Paginator;
import com.ymos.entity.Query;
import com.ymos.biz.BaseService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public abstract class ListController<T extends Id,Q extends Query,S extends BaseService<T>> {
	protected final String NAV_LIST_PAGE;
	protected final String LIST_PAGE;
	protected final String SLEF_DEF_PAGE="self_define_page";
	protected S service;
	public ListController(String model) {
       this.LIST_PAGE=String.format("%s/list", new Object[]{model});
       this.NAV_LIST_PAGE=String.format("%s/list", new Object[]{model});
	}
	@RequestMapping({"/list"})
	public String list(Q query, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		return this.innerList(initPage(request), query, model, request, response);
	}
	private int initPage(HttpServletRequest request){
		String page=request.getParameter("_page");
		if (page!=null&&!page.isEmpty()) {
			try {
				return Integer.parseInt(page);
			} catch (Exception e) {
               return -1;
			}
		}else{
			return -1;
		}
	}
	@RequestMapping({"/list/{page}"})
	public String list(@PathVariable("page") int page, Q query, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		return this.innerList(page, query, model, request, response);
	}
	protected Paginator intiPaginator(int page, Q query){
		Paginator paginator=new Paginator(page);
		paginator.setQuery(query);
		paginator.setPath(this.NAV_LIST_PAGE);
		return paginator;
	}
	protected String innerList(int page, Q query, Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
		Paginator paginator=this.intiPaginator(page, query);
		List<T> datas=Collections.emptyList();
		boolean pre=this.preList(query,paginator, model, request, response);
		if (pre) {
			try {
				datas=this.service.select(paginator);
			}catch (Exception e){
				e.printStackTrace();
			}

			paginator=this.service.initPage(paginator,datas);
		}
		model.addAttribute("datas",datas);
		model.addAttribute("paginator",paginator);
		model.addAttribute("hasDatas",Boolean.valueOf(datas!=null&&!datas.isEmpty()));
		this.afterList(query, paginator,model, request, response);
		return model.containsAttribute(SLEF_DEF_PAGE)?(String)model.asMap().get(SLEF_DEF_PAGE):this.LIST_PAGE;
	}
	protected void setSelfDefPage(Model model, String pagePath){
		model.addAttribute(SLEF_DEF_PAGE, pagePath);
	}
	protected boolean preList(Q query, Paginator paginator, Model model, HttpServletRequest request, HttpServletResponse response){
		return true;
	}
	protected void afterList(Q query, Paginator paginator, Model model, HttpServletRequest request, HttpServletResponse response){
		
	}
}

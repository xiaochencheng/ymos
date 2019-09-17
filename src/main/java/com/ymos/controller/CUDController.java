package com.ymos.controller;

import com.google.common.base.Strings;
import com.ymos.entity.Id;
import com.ymos.entity.IdForm;
import com.ymos.entity.Query;
import com.ymos.biz.BaseService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public abstract class CUDController<T extends Id, Q extends Query, F extends IdForm<T>, S extends BaseService<T>> extends ListController<T, Q, S> {

    protected final String CREATE_MODIFY_PAGE;
    protected final String REDIRECT_PAGE;

    public CUDController(String model) {
        super(model);
        this.CREATE_MODIFY_PAGE = String.format("/%s/create", new Object[]{model});
        this.REDIRECT_PAGE = String.format("redirect:/%s/list", new Object[]{model});
    }

    @RequestMapping({"/create"})
    public String create(Model model, HttpServletRequest request) {
        this.postCreate(model);
        return this.CREATE_MODIFY_PAGE;
    }

    protected abstract void innerSave(F form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response);

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    protected String save(@Valid F form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("form", form);
        //String queryStr= request.getParameter("_queryStr");
        String queryString = request.getQueryString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + request.getQueryString());
        queryString = queryString.substring(8);

        if (!errors.hasErrors()) {
            this.innerSave(form, errors, model, request, response);
        }
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            this.onSaveError(form, errors, model, request, response);
            return this.CREATE_MODIFY_PAGE;
        } else {
            return Strings.isNullOrEmpty(queryString) ? this.REDIRECT_PAGE : this.REDIRECT_PAGE + "?" + queryString;
        }
    }

    protected void onSaveError(F form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(value = {"/delete/{ids}"}, method = {RequestMethod.POST})
    protected String delete(@PathVariable("ids") String ids, HttpServletRequest request) {
        this.service.deleteByIds(ids);
        String queryStr = request.getParameter("_queryStr");
        return Strings.isNullOrEmpty(queryStr) ? this.REDIRECT_PAGE : this.REDIRECT_PAGE + "?" + queryStr;
    }

    @RequestMapping({"/modify/{id}"})
    protected String update(@PathVariable String id, Model model, HttpServletRequest request) {
        T obj = this.service.selectById(id);
        model.addAttribute("form", obj);
        String queryStr = request.getParameter("_queryStr");
        model.addAttribute("queryStr", queryStr);
        this.postUpdate(id, obj, model);
        return this.CREATE_MODIFY_PAGE;
    }

    @RequestMapping({"/review/{id}"})
    protected String review(@PathVariable String id, Model model, HttpServletRequest request) {
        T obj = this.service.selectById(id);
        model.addAttribute("form", obj);
        String queryStr = request.getParameter("_queryStr");
        model.addAttribute("queryStr", queryStr);
        this.postUpdate(id, obj, model);
        return this.CREATE_MODIFY_PAGE;
    }

    protected void postUpdate(String id, T obj, Model model) {
    }

    protected void postCreate(Model model) {
    }

}

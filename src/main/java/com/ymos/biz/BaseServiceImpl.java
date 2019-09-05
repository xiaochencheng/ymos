package com.ymos.biz;



import com.ymos.entity.Id;
import com.ymos.entity.Paginator;
import com.ymos.mapper.BaseMapper;
import com.ymos.biz.BaseService;

import java.util.List;

public class BaseServiceImpl<T extends Id> implements BaseService<T> {

    protected BaseMapper<T> mapper;
    public BaseServiceImpl(){};

    @Override
    public T selectById(String id) {
        return this.mapper.selectById(id);
    }

    @Override
    public List<T> selectAll() {

        return this.mapper.selectAll();
    }

    @Override
    public List<T> select(Paginator page) {
        List<T> list=this.mapper.select(page);
        return list;
    }
    @Override
    public <K> List<K> initPaginator(Paginator page, List<K> list) {
        int total=this.mapper.count(page);
        int size=(page.getPage()-1)*page.getSize()+list.size();
        if (list!=null&&!list.isEmpty()) {
            page.setHasData(true);
            /*if (list.size()>=page.getSize()) {*/
            if (size<total && list.size()==page.getSize()) {
                page.setHasNextPage(true);
                return list.subList(0,list.size()-1);
            }else{
                page.setHasNextPage(false);
                return list;
            }
        }else{
            page.setHasData(false);
            page.setHasNextPage(false);
            return list;
        }
    }

    @Override
    public Paginator initPage(Paginator page, List<T> list) {
        list=this.initPaginator(page, list);
        if (page.isNeedTotal()) {
            if (page.getPage()<=1&&!page.isHasNextPage()) {
                page.setTotal(list.size());
            }else{
                int total=this.mapper.count(page);
                page.setTotal(total);
            }
        }
        return page;
    }
    @Override
    public int deleteById(String id) {

        return this.mapper.deleteById(id);
    }

    @Override
    public int deleteByIds(String ids) {
        return this.mapper.deleteByIds(ids.split("-"));
    }

    @Override
    public int update(T t) {

        return this.mapper.update(t);
    }

    @Override
    public int save(T t) {
        return this.mapper.insert(t);
    }

    @Override
    public int saveOrUpdate(T t) {

        return t.modified()?this.update(t):this.save(t);
    }
}

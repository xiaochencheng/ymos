package com.ymos.biz;


import com.ymos.entity.Id;
import com.ymos.entity.Paginator;

import java.util.List;

public interface BaseService<T extends Id>{
     Paginator initPage(Paginator page, List<T> list);

     T selectById(String id);

     List<T> selectAll();

     List<T> select(Paginator page);

     <K> List<K> initPaginator(Paginator page, List<K> list);

     int deleteById(String id);

     int deleteByIds(String ids);

     int update(T t);

     int save(T t);

     int saveOrUpdate(T t);
}

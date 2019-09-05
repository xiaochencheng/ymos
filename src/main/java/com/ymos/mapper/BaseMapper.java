package com.ymos.mapper;



import com.ymos.entity.Id;
import com.ymos.entity.Paginator;

import java.util.List;


public interface BaseMapper<T extends Id>{
     int deleteById(String id);
     int deleteByIds(String ids[]);
     int insert(T var);
     List<T> selectAll();
     List<T> select(Paginator page);
     T selectById(String id);
	 int count(Paginator page);
	 int update(T var);
}

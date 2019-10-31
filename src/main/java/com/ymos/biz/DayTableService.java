package com.ymos.biz;

import com.ymos.entity.DayTable;
import com.ymos.entity.DayTableReport;
import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DayTableService extends BaseService<DayTable> {

    List<DayTableReport> exportExcel(DayTableReport dayTableReport);

    List<DayTable> selectFind();

    int create(List<DayTable> dayTables);

    int update(List<DayTable> dayTables);

}



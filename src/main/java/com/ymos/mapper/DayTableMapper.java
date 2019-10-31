package com.ymos.mapper;

import com.ymos.entity.DayTable;
import com.ymos.entity.DayTableReport;
import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface DayTableMapper extends BaseMapper<DayTable> {

    List<DayTableReport> exportExcel(DayTableReport dayTableReport);

    List<DayTable> selectFind();

    int create(List<DayTable> dayTables);

    int update(List<DayTable> dayTables);
}

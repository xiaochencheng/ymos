package com.ymos.biz;

import com.ymos.entity.DayTable;
import com.ymos.entity.DayTableReport;
import com.ymos.entity.Order;
import com.ymos.entity.OrderReport;
import com.ymos.mapper.DayTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("dayTableService")
public class DayTableServiceImpl extends BaseServiceImpl<DayTable> implements DayTableService {

    DayTableMapper dayTableMapper;

    @Resource
    public void setDayTableMapper(DayTableMapper dayTableMapper) {
        this.dayTableMapper = dayTableMapper;
        this.mapper=dayTableMapper;
    }

    @Override
    public List<DayTableReport> exportExcel(DayTableReport dayTableReport) {
        List<DayTableReport> list=dayTableMapper.exportExcel(dayTableReport);
        return list;
    }

    @Override
    public List<DayTable> selectFind() {
        List<DayTable> dayTable=dayTableMapper.selectFind();
        return dayTable;
    }

    @Override
    public int create(List<DayTable> dayTables) {
        int count=dayTableMapper.create(dayTables);
        return count;
    }

    @Override
    public int update(List<DayTable> dayTables) {
        int count=dayTableMapper.update(dayTables);
        return count;
    }
}

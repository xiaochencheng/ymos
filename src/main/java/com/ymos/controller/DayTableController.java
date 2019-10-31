package com.ymos.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymos.biz.DayTableService;
import com.ymos.common.Constants;
import com.ymos.entity.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dayTable")
public class DayTableController extends CUDController<DayTable, DayTableQuery, DayTableForm, DayTableService> {

    DayTableService dayTableService;

    @Autowired
    public void setDayTableService(DayTableService dayTableService) {
        this.dayTableService = dayTableService;
        this.service = dayTableService;
    }


    public DayTableController() {
        super("dayTable");
    }

    @Override
    protected void innerSave(DayTableForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    protected String innerList(int page, DayTableQuery query, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Paginator paginator = this.intiPaginator(page, query);
        List<DayTable> datas = Collections.emptyList();
        List<DayTable> table = dayTableService.selectFind();
        List<DayTable> list = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            DayTable dayTable = new DayTable();
            dayTable.setBgh(table.get(i).getBgh());
            dayTable.setDpzh(table.get(i).getDpzh());
            dayTable.setFksj(table.get(i).getFksj().substring(0,10));
            dayTable.setOrderMoney(table.get(i).getOrderMoney());
            list.add(dayTable);
        }
        dayTableService.create(list);

        boolean pre = preList(query, paginator, model, request, response);
        if (pre) {
            try {
                datas = this.service.select(paginator);
            } catch (Exception e) {
                e.printStackTrace();
            }

            paginator = this.service.initPage(paginator, datas);
        }
        //String json=  JSON.toJSONString(datas);
        //System.out.println(json);
        model.addAttribute("datas", datas);
        model.addAttribute("paginator", paginator);
        model.addAttribute("hasDatas", Boolean.valueOf(datas != null && !datas.isEmpty()));
        this.afterList(query, paginator, model, request, response);
        return "dayTable/list";
    }

    protected Paginator intiPaginator(int page, DayTableQuery query) {
        Paginator paginator = new Paginator(page);
        paginator.setQuery(query);
        paginator.setPath(this.NAV_LIST_PAGE);
        return paginator;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<DayTable> update(@RequestBody List<DayTable> dayTable,@RequestParam String name, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        System.out.println(dayTable.size());
        List<DayTable> list=new ArrayList<>();
        for (int i=1;i<dayTable.size();i++){
            DayTable dayTable1=new DayTable();
            dayTable1.setCaiguo(dayTable.get(i).getCaiguo());
            dayTable1.setSalesLirun(dayTable.get(i).getSalesLirun());
            dayTable1.setYugulirun(dayTable.get(i).getYugulirun());
            dayTable1.setToufang(dayTable.get(i).getToufang());
            dayTable1.setToufangChen(dayTable.get(i).getToufangChen());
            dayTable1.setWuliuChen(dayTable.get(i).getWuliuChen());
            dayTable1.setWuliuMoney(dayTable.get(i).getWuliuMoney());
            dayTable1.setRate(dayTable.get(i).getRate());
            dayTable1.setCaiguoChen(dayTable.get(i).getCaiguoChen());
            dayTable1.setSalesMoney(dayTable.get(i).getSalesMoney());
            dayTable1.setChannelFeel(dayTable.get(i).getChannelFeel());
            dayTable1.setChenBen(dayTable.get(i).getChenBen());
            dayTable1.setDpzh(dayTable.get(i).getDpzh());
            dayTable1.setFksj(dayTable.get(i).getFksj());
            dayTable1.setId(dayTable.get(i).getId());
            list.add(dayTable1);
        }
         dayTableService.update(list);


        return new Result<DayTable>().setData(new DayTable()).setFlag(true);
    }


    /**
     * Excel表格导出方法
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(DayTableReport dayTableReport, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (dayTableReport.getChannelId() == null && dayTableReport.getCreateDate() == null ||
                    dayTableReport.getOperator() == null && dayTableReport.getOperator() == "" ||
                    dayTableReport.getEndDate() == null && dayTableReport.getEndDate() == "" ||
                    dayTableReport.getCountry() == null && dayTableReport.getCountry() == "" ||
                    dayTableReport.getBeginDate() == null && dayTableReport.getBeginDate() == "") {
                dayTableReport.setBeginDate(Constants.yyyyMMdd.format(new Date()));
            }
            String filename = dayTableReport.getTitle() + Constants.yyyyMMdd.format(new Date()) + ".xls";
//            filename=new String(filename.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            exportExcel(response.getOutputStream(), dayTableReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcel(ServletOutputStream outputStream, DayTableReport dayTableReport) throws IOException {
        org.apache.poi.ss.usermodel.Workbook wk = new HSSFWorkbook();
        String count = null;
        try {
            //List<Review> list = reviewService.AllCountry();
            List<DayTableReport> cpsChannelList = dayTableService.exportExcel(dayTableReport);
            System.out.println(cpsChannelList.size());
            //HSSFCellStyle hssfCellStyle=((HSSFWorkbook) wk).createCellStyle();//设置Excel表格样式
            //创建工作表
            org.apache.poi.ss.usermodel.Sheet sheet = wk.createSheet(dayTableReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("日期");
            headerNames.add("店铺账号");
            headerNames.add("汇率");
            headerNames.add("订单数量");
            headerNames.add("订单金额");
            headerNames.add("销售金额");
            headerNames.add("通道费4.4%+2%");
            headerNames.add("物流运费");
            headerNames.add("物流成本占比");
            headerNames.add("采购合计");
            headerNames.add("采购成本占比");
            headerNames.add("投放花费");
            headerNames.add("投放成本占比");
            headerNames.add("销售利润");
            headerNames.add("成本利润率");
            headerNames.add("销售利润率");
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);
            columnsWidths.add(3000);

            org.apache.poi.ss.usermodel.Cell cell = null;
            int i = 0;
            for (; i < headerNames.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(headerNames.get(i));
                //设置列宽
                sheet.setColumnWidth(i, columnsWidths.get(i));
            }
            i = 1;
            for (DayTableReport report : cpsChannelList) {
                //int j=0;
                row = sheet.createRow(i);
                if (row == null) {
                    continue;
                }

                row.createCell(0).setCellValue(report.getFksj());
                row.createCell(1).setCellValue(report.getDpzh());
                row.createCell(2).setCellValue(report.getRate());
                row.createCell(3).setCellValue(report.getBgh());
                row.createCell(4).setCellValue( report.getOrderMoney());
                row.createCell(5).setCellValue( report.getSalesMoney());
                row.createCell(6).setCellValue( report.getChannelFeel());
                row.createCell(7).setCellValue( report.getWuliuMoney());
                row.createCell(8).setCellValue( report.getWuliuChen());
                row.createCell(9).setCellValue( report.getCaiguo());
                row.createCell(10).setCellValue( report.getCaiguoChen());
                row.createCell(11).setCellValue( report.getToufang());
                row.createCell(12).setCellValue( report.getToufangChen());//=F4-H4-J4-L4-G4
                row.createCell(13).setCellValue( report.getYugulirun());//=(H4+J4+L4+G4)/F4
                row.createCell(14).setCellValue( report.getChenBen());//=N4/F4
                row.createCell(15).setCellValue( report.getSalesLirun());
                i++;
            }
            wk.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //wk.close();
        }
    }


    @Override
    protected boolean preList(DayTableQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }


}

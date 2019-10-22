package com.ymos.controller;

import com.ymos.biz.DayTableService;
import com.ymos.common.Constants;
import com.ymos.entity.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dayTable")
public class DayTableController extends CUDController<Order, OrderQuery, OrderForm, DayTableService> {

    DayTableService dayTableService;

    @Autowired
    public void setDayTableService(DayTableService dayTableService) {
        this.dayTableService = dayTableService;
        this.service=dayTableService;
    }


    public DayTableController() {
        super("dayTable");
    }

    @Override
    protected void innerSave(OrderForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * Excel表格导出方法
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(OrderReport orderReport, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (orderReport.getChannelId() == null && orderReport.getCreateDate() == null ||
                    orderReport.getOperator() == null && orderReport.getOperator() == "" ||
                    orderReport.getEndDate() == null && orderReport.getEndDate() == "" ||
                    orderReport.getCountry() == null && orderReport.getCountry() == "" ||
                    orderReport.getBeginDate() == null && orderReport.getBeginDate() == "") {
                orderReport.setBeginDate(Constants.yyyyMMdd.format(new Date()));
            }
            String filename = orderReport.getTitle() + Constants.yyyyMMdd.format(new Date()) + ".xls";
//            filename=new String(filename.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            exportExcel(response.getOutputStream(), orderReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcel(ServletOutputStream outputStream, OrderReport orderReport) throws IOException {
        org.apache.poi.ss.usermodel.Workbook wk = new HSSFWorkbook();
        String count = null;
        try {
            //List<Review> list = reviewService.AllCountry();
            List<OrderReport> cpsChannelList = dayTableService.exportExcel(orderReport);
            System.out.println(cpsChannelList.size());
            //HSSFCellStyle hssfCellStyle=((HSSFWorkbook) wk).createCellStyle();//设置Excel表格样式
            //创建工作表
            org.apache.poi.ss.usermodel.Sheet sheet = wk.createSheet(orderReport.getTitle());
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
            headerNames.add("Shopify营业额参考");

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
            for (OrderReport report : cpsChannelList) {
                //int j=0;
                row = sheet.createRow(i);
                if (row == null) {
                    continue;
                }
                row.createCell(0).setCellValue(report.getFksj());
                row.createCell(1).setCellValue(report.getDpzh());
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue(report.getBgh());
                row.createCell(4).setCellValue(Double.valueOf(report.getOrderMoney()));
                row.createCell(5).setCellFormula("PRODUCT(C"+(i+1)+",E"+(i+1)+")");
                //row.createCell(5).setCellValue(row.createCell(2).getCellType()+"*"+row.createCell(4).getCellType());
                row.createCell(6).setCellFormula("PRODUCT(F"+(i+1)+",0.064)");
                row.createCell(7).setCellValue("");
                row.createCell(8).setCellFormula("H"+(i+1)+"/F"+(i+1));
                row.createCell(9).setCellValue("");

                row.createCell(10).setCellFormula("J"+(i+1)+"/F"+(i+1));
                row.createCell(11).setCellValue("");
                row.createCell(12).setCellFormula("L"+(i+1)+"/F"+(i+1));
                row.createCell(13).setCellFormula("F"+(i+1)+"-H"+(i+1)+"-J"+(i+1)+"-L"+(i+1)+"-G"+(i+1));//=F4-H4-J4-L4-G4
                row.createCell(14).setCellFormula("(H"+(i+1)+"+J"+(i+1)+"+L"+(i+1)+"+G"+(i+1)+")/F"+(i+1));//=(H4+J4+L4+G4)/F4
                row.createCell(15).setCellFormula("N"+(i+1)+"/F"+(i+1));//=N4/F4
                row.createCell(16).setCellValue("");
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
    protected boolean preList(OrderQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }


}

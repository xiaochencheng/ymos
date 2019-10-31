package com.ymos.controller;

import com.ymos.biz.WuLiuService;
import com.ymos.biz.WuliuInfoService;
import com.ymos.common.Constants;
import com.ymos.entity.*;
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
@RequestMapping("/wuliuInfo")
public class WuliuInfoController extends CUDController<Order,OrderQuery,OrderForm, WuliuInfoService>{

    WuliuInfoService wuliuInfoService;

    @Autowired
    public void setWuliuInfoService(WuliuInfoService wuliuInfoService) {
        this.wuliuInfoService = wuliuInfoService;
        this.service=wuliuInfoService;
    }

    public WuliuInfoController() {
        super("wuliuInfo");
    }

    @Override
    protected void innerSave(OrderForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected boolean preList(OrderQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
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
            List<OrderReport> cpsChannelList = wuliuInfoService.exportExcel(orderReport);
            System.out.println(cpsChannelList.size());

            //创建工作表
            org.apache.poi.ss.usermodel.Sheet sheet = wk.createSheet(orderReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("包裹号");
            headerNames.add("订单号");
            headerNames.add("运单号");
            headerNames.add("物流状态");
            headerNames.add("店铺账号");
            headerNames.add("发货时间");
            headerNames.add("付款时间");
            headerNames.add("物流方式");
            headerNames.add("物流最新信息");
            headerNames.add("运输时间");

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
                row.createCell(0).setCellValue(report.getBgh());
                row.createCell(1).setCellValue(report.getOrderId());
                row.createCell(2).setCellValue(report.getYdh());
                row.createCell(3).setCellValue(report.getOrderStatus());
                row.createCell(4).setCellValue(report.getDpzh());
                row.createCell(5).setCellValue(report.getFhsj());
                row.createCell(6).setCellValue(report.getFksj());
                row.createCell(7).setCellValue(report.getWlfs());
                row.createCell(8).setCellValue(report.getWuliu());
                row.createCell(9).setCellValue(report.getItemTimeLength());
                i++;
            }
            wk.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //wk.close();
        }
    }


}

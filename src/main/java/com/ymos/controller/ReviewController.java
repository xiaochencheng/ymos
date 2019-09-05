package com.ymos.controller;


import com.ymos.common.Constants;
import com.ymos.entity.*;
import com.ymos.biz.ReviewService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rev")
public class ReviewController extends CUDController<Review, ReviewQuery, ReviewForm, ReviewService> {


     ReviewService reviewService;


    public ReviewController() {
        super("rev");
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
        this.service=reviewService;
    }



    @Override
    protected void innerSave(ReviewForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected boolean preList(ReviewQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

    /**
     * Excel表格导出方法
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(ReviewReport reviewReport, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (reviewReport.getChannelId() == null && reviewReport.getCreateDate() == null ||
                    reviewReport.getOperator() == null && reviewReport.getOperator() == "" ||
                    reviewReport.getEndDate() == null && reviewReport.getEndDate() == "" ||
                    reviewReport.getCountry() == null && reviewReport.getCountry() == "" ||
                    reviewReport.getBeginDate() == null && reviewReport.getBeginDate() == "") {
                reviewReport.setBeginDate(Constants.yyyyMMdd.format(new Date()));
            }
            String filename = reviewReport.getTitle() + Constants.yyyyMMdd.format(new Date()) + ".xls";
//            filename=new String(filename.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            exportExcel(response.getOutputStream(), reviewReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcel(ServletOutputStream outputStream, ReviewReport reviewReport) throws IOException {
        Workbook wk = new HSSFWorkbook();
        String count = null;
        try {
            //List<Review> list = reviewService.AllCountry();
            List<ReviewReport> cpsChannelList = reviewService.queryExcelData(reviewReport);
            System.out.println(cpsChannelList.size());

            //创建工作表
            Sheet sheet = wk.createSheet(reviewReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("id");
            headerNames.add("SPU");
            headerNames.add("产品中文名称");
            headerNames.add("产品中英名称");
            headerNames.add("报关中文名");
            headerNames.add("报关英文名");
            headerNames.add("报关价格");
            headerNames.add("报关重量");
            headerNames.add("产品分类");
            headerNames.add("图片URL");
            headerNames.add("建议采购价");
            headerNames.add("重量");
            headerNames.add("采购网址");
            headerNames.add("预售价");
            headerNames.add("运费");
            headerNames.add("状态");
            headerNames.add("备注");

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

            Cell cell = null;
            int i = 0;
            for (; i < headerNames.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(headerNames.get(i));
                //设置列宽
                sheet.setColumnWidth(i, columnsWidths.get(i));
            }
            i = 1;
            for (ReviewReport report : cpsChannelList) {
                //int j=0;
                row = sheet.createRow(i);
                if (row == null) {
                    continue;
                }
                row.createCell(0).setCellValue(report.getId());
                row.createCell(1).setCellValue(report.getSpu());
                row.createCell(2).setCellValue(report.getPro_ch_name());
                row.createCell(3).setCellValue(report.getPro_en_name());
                row.createCell(4).setCellValue(report.getCus_ch_name());
                row.createCell(5).setCellValue(report.getCus_en_name());
                row.createCell(6).setCellValue(report.getCus_price());
                row.createCell(7).setCellValue(report.getCus_weight());
                row.createCell(8).setCellValue(report.getPro_list());
                row.createCell(9).setCellValue(report.getPro_url());
                row.createCell(10).setCellValue(report.getPro_purchase_price());
                row.createCell(11).setCellValue(report.getWeight());
                row.createCell(12).setCellValue(report.getUrl());
                row.createCell(13).setCellValue(report.getPresale_price());
                row.createCell(14).setCellValue(report.getFreight());
                if (report.getStatus()==1) {
                    row.createCell(15).setCellValue("通过");
                } else if (report.getStatus()==2) {
                    row.createCell(15).setCellValue("未通过");
                } else if (report.getStatus()==3) {
                    row.createCell(15).setCellValue("未审核");
                }
                row.createCell(16).setCellValue(report.getRemark());
                i++;
            }
            wk.write(outputStream);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            wk.close();
        }
    }



}

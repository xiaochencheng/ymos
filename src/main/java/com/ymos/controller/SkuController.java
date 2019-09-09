package com.ymos.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymos.common.Constants;
import com.ymos.common.LoginContext;
import com.ymos.entity.*;
import com.ymos.biz.SkuListService;
import com.ymos.biz.SkuService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sku")
public class SkuController extends CUDController<Sku, SkuQuery, SkuForm, SkuService> {

    SkuService skuService;

    SkuListService skuListService;

    private static String[][] xyz = new String[3][];
    private static int counterIndex = xyz.length - 1;
    private static int[] counter = {0, 0, 0};

    @Autowired
    public void setSkuListService(SkuListService skuListService) {
        this.skuListService = skuListService;
    }

    @Autowired
    public void setSkuService(SkuService skuService) {
        this.skuService = skuService;
        this.service = skuService;
    }

    public SkuController() {
        super("sku");
    }

    @Override
    protected void innerSave(SkuForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected boolean preList(SkuQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

    //@ResponseBody
    //@RequestMapping("/skulist")
    //protected String innerList(int page,SkuQuery query,Model model,HttpServletRequest request,HttpServletResponse response){
    //
    //    Paginator paginator=this.intiPaginator(page,query);
    //    if(query.getSize()!=null){
    //        paginator.setSize(query.getSize());
    //    }
    //    List<Sku> datas= Collections.emptyList();
    //    boolean pre=this.preList(query,paginator,model,request,response);
    //    if(pre){
    //        datas=this.service.select(paginator);
    //        paginator=this.service.initPage(paginator,datas);
    //    }
    //    model.addAttribute("query",query);
    //    model.addAttribute("datas",datas);
    //    model.addAttribute("paginator",paginator);
    //    model.addAttribute("hasDatas",Boolean.valueOf(datas!=null && !datas.isEmpty()));
    //    this.afterList(query,paginator,model,request,response);
    //    return "sku/skulist";
    //}


    @ResponseBody
    @RequestMapping("/spu")
    public List<Product> getSpuName() {
        List<Product> list = skuService.getFindSPU();
        return list;
    }

    public static String createJsonString(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject.toString();
    }

    /**
     * 新增产品数据
     */
    @ResponseBody
    @RequestMapping(value = "/create1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<Sku> create(@RequestBody List<Sku> ids, @RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new Result<Sku>().setData(new Sku()).setFlag(false);
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(name);
        String keyChName = jsonObject.toString();
        for (int i = 0; i < ids.size(); i++) {

            System.out.println(keyChName + ">>>>>>>>>>>>");
            Sku sku = new Sku();
            User user = LoginContext.getUser(session);
            String creator = user.getUsername();
            String sdate;
            Date ddate = new Date();
            sdate = (new SimpleDateFormat("yyyy-MM-dd")).format(ddate);

            //System.out.println(ids.size());
            String spu = ids.get(i).getSku();
            String spuName = spu.substring(0, spu.indexOf("-"));
            sku.setName(ids.get(i).getName());
            sku.setNameEn(ids.get(i).getNameEn());
            sku.setNameCnBg(ids.get(i).getNameCnBg());
            sku.setNameEnBg(ids.get(i).getNameEnBg());
            sku.setSku(ids.get(i).getSku());
            sku.setPriceBg(ids.get(i).getPriceBg());
            sku.setImgUrl(ids.get(i).getImgUrl());
            sku.setPrice(ids.get(i).getPrice());
            sku.setSpu(spuName);
            sku.setDangerDesBg(ids.get(i).getDangerDesBg());
            sku.setHgbmBg(ids.get(i).getHgbmBg());
            sku.setWeight(ids.get(i).getWeight());
            sku.setCreate_date(sdate);
            sku.setSbm(ids.get(i).getSbm());
            sku.setSourceUrl(ids.get(i).getSourceUrl());
            sku.setWeightBg(ids.get(i).getWeightBg());
            sku.setCreator(creator);
            sku.setAttributes(keyChName);
            skuService.create(sku);


        }
        return new Result<Sku>().setData(new Sku()).setFlag(true);
    }

/*    @ResponseBody
    @RequestMapping("/skuChName")
    public String getskuChName(@RequestBody List<Sku> skuChName,HttpServletResponse response,HttpServletRequest request){
        System.out.println(skuChName.size());
        for (int j=0;j<skuChName.size();j++){
            skuChName.get(j).getAttributes();
        }
     return  null;
    }*/

    /**
     * Excel表格导出方法
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(SkuReport skuReport, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (skuReport.getChannelId() == null && skuReport.getCreateDate() == null ||
                    skuReport.getOperator() == null && skuReport.getOperator() == "" ||
                    skuReport.getEndDate() == null && skuReport.getEndDate() == "" ||
                    skuReport.getCountry() == null && skuReport.getCountry() == "" ||
                    skuReport.getBeginDate() == null && skuReport.getBeginDate() == "") {
                skuReport.setBeginDate(Constants.yyyyMMdd.format(new Date()));
            }
            String filename = skuReport.getTitle() + Constants.yyyyMMdd.format(new Date()) + ".xls";
//            filename=new String(filename.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            exportExcel(response.getOutputStream(), skuReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcel(ServletOutputStream outputStream, SkuReport skuReport) throws IOException {
        Workbook wk = new HSSFWorkbook();
        String count = null;
        try {
            //List<Review> list = reviewService.AllCountry();
            List<SkuReport> cpsChannelList = skuService.exportExcel(skuReport);
            System.out.println(cpsChannelList.size());

            //创建工作表
            Sheet sheet = wk.createSheet(skuReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("sku(必填)");
            headerNames.add("平台SKU");
            headerNames.add("识别码");
            headerNames.add("商品编码");
            headerNames.add("商品名称");
            headerNames.add("商品状态");
            headerNames.add("图片URL");
            headerNames.add("实际重量（g）");
            headerNames.add("采购价（RMB）");
            headerNames.add("采购员");
            headerNames.add("长(cm)");
            headerNames.add("宽(cm)");
            headerNames.add("高(cm)");
            headerNames.add("来源url（超始值为：http://）");
            headerNames.add("备注");
            headerNames.add("英文报关");
            headerNames.add("中文报关");
            headerNames.add("申报重量（g）");
            headerNames.add("申报金额（USD）");
            headerNames.add("危险运输品");
            headerNames.add("海关编码");
            headerNames.add("创建时间");

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
            for (SkuReport report : cpsChannelList) {
                //int j=0;
                row = sheet.createRow(i);
                if (row == null) {
                    continue;
                }
                row.createCell(0).setCellValue(report.getSkuName());
                row.createCell(1).setCellValue(report.getSkuName());
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue("");
                row.createCell(4).setCellValue(report.getName());
                row.createCell(5).setCellValue("在售");
                row.createCell(6).setCellValue("");
                row.createCell(7).setCellValue(report.getWeight());
                row.createCell(8).setCellValue(report.getPrice());
                row.createCell(9).setCellValue("无");
                row.createCell(10).setCellValue("0");
                row.createCell(11).setCellValue("0");
                row.createCell(12).setCellValue("0");
                row.createCell(13).setCellValue(report.getSourceUrl());
                row.createCell(14).setCellValue("");
                row.createCell(15).setCellValue(report.getNameEnBg());
                row.createCell(16).setCellValue(report.getNameCnBg());
                row.createCell(17).setCellValue(report.getWeightBg());
                row.createCell(18).setCellValue(report.getPriceBg());
                row.createCell(19).setCellValue(report.getDangerDesBg());
                row.createCell(20).setCellValue(report.getHgbmBg());
                row.createCell(21).setCellValue(report.getCreate_date());
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

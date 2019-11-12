package com.ymos.controller;


import com.ymos.biz.SkuListService;
import com.ymos.biz.SkuService;
import com.ymos.common.Constants;
import com.ymos.common.LoginContext;
import com.ymos.entity.*;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public  List<Product> getSpuName() {
        List<Product> list = skuService.getFindSPU();
        return list;
    }





    /**
     * 递归实现
     * 原理：从原始list的0开始依次遍历到最后
     *
     * @param originalList 原始list
     * @param position     当前递归在原始list的position
     * @param returnList   返回结果
     * @param cacheList    临时保存的list
     */
    private static <T> void descartesRecursive(List<List<T>> originalList, int position, List<List<T>> returnList, List<T> cacheList) {
        List<T> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            //最后一个复用cacheList，节省内存
            List<T> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {//遍历到最后退出递归
                returnList.add(childCacheList);
                continue;
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList);
        }
    }

    private static <T> List<List<T>> getDescartes(List<List<T>> list) {
        List<List<T>> returnList = new ArrayList<>();
        descartesRecursive(list, 0, returnList, new ArrayList<T>());
        return returnList;
    }

    /**
     * 新增产品数据
     */
    @ResponseBody
    @RequestMapping(value = "/create1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<Sku> create(@RequestBody List<Sku> ids, @RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            //name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
            //JSONObject jsonObject = (JSONObject) JSONObject.parse(name);
            //String keyChName = jsonObject.toString();

           // System.out.println(ids.size()+">>>>>>>>>>>>");
            for (int i = 0; i < ids.size(); i++) {
                Sku sku = new Sku();
                User user = LoginContext.getUser(session);
                String creator = user.getUsername();
                String sdate;
                Date ddate = new Date();
                sdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ddate);

                //System.out.println(ids.size());
                String spu = ids.get(i).getSku();
                String spuName = spu.substring(0, spu.indexOf("-"));
                //String attri=spu.substring(spuName.length()+1,spu.length());
                sku.setName(ids.get(i).getName());
                sku.setNameEn(ids.get(i).getNameEn());
                sku.setNameCnBg(ids.get(i).getNameCnBg());
                sku.setNameEnBg(ids.get(i).getNameEnBg());
                sku.setSkuName(ids.get(i).getSku());
                sku.setPriceBg(ids.get(i).getPriceBg());
                sku.setImgUrl(ids.get(i).getImgUrl());
                sku.setPrice(ids.get(i).getPrice());
                sku.setSpu(spuName);
                int dangerDesBg= Integer.parseInt(ids.get(i).getDangerDesBg());
                switch (dangerDesBg)
                {
                    case 0:
                        sku.setDangerDesBg("无");
                        break;
                    case 1:
                        sku.setDangerDesBg("含电");
                        break;
                     case 2:
                        sku.setDangerDesBg("液体");
                        break;
                     case 3:
                        sku.setDangerDesBg("粉末");
                        break;
                     case 4:
                        sku.setDangerDesBg("纯电");
                        break;
                     case 5:
                        sku.setDangerDesBg("膏体");
                        break;
                     case 6:
                        sku.setDangerDesBg("带磁");
                        break;

                }

                sku.setHgbmBg(ids.get(i).getHgbmBg());
                sku.setWeight(ids.get(i).getWeight());
                sku.setCreate_date(sdate);
                sku.setSbm(ids.get(i).getSbm());
                sku.setSourceUrl(ids.get(i).getSourceUrl());
                sku.setWeightBg(ids.get(i).getWeightBg());
                sku.setCreator(creator);
                String att=ids.get(i).getAttributes();
                String attributes=att.substring(0,att.length()-1);
                sku.setAttributes(attributes);
                skuService.create(sku);

            //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<Sku>().setData(new Sku()).setFlag(false);
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

    @ResponseBody
    @RequestMapping("/update")
    public Result<Sku> update(HttpServletRequest request, HttpServletResponse response, Sku sku,HttpSession session) {
        String sp = sku.getId();
        User user = LoginContext.getUser(session);
        String creator = user.getUsername();
        int ids=Integer.parseInt(sku.getDangerDesBg());
        switch (ids){
            case 0:
                sku.setDangerDesBg("无");
                break;
             case 1:
                sku.setDangerDesBg("含电");
                break;
             case 2:
                sku.setDangerDesBg("液体");
                break;
             case 3:
                sku.setDangerDesBg("粉末");
                break;
             case 4:
                sku.setDangerDesBg("纯电");
                break;
             case 5:
                sku.setDangerDesBg("膏体");
                break;
             case 6:
                sku.setDangerDesBg("带磁");
                break;

        }
         sku.setCreator(creator);
        skuService.update(sku);
        return new Result<Sku>().setData(sku).setFlag(true);
    }


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
            //System.out.println(cpsChannelList.size());

            //创建工作表
            Sheet sheet = wk.createSheet(skuReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("*sku(必填)");
            headerNames.add("平台SKU");
            headerNames.add("识别码");
            headerNames.add("商品编码");
            headerNames.add("商品名称");
            headerNames.add("图片URL（必须以http://或https：//开头）");
            headerNames.add("商品重量（g）");
            headerNames.add("采购价（RMB）");
            headerNames.add("采购员（输入子账号姓名或名称）");
            headerNames.add("长(cm)");
            headerNames.add("宽(cm)");
            headerNames.add("高(cm)");
            headerNames.add("来源URL（必须以http://或https：//开头）");
            headerNames.add("备注");
            headerNames.add("英文报关");
            headerNames.add("中文报关");
            headerNames.add("申报重量（g）");
            headerNames.add("申报金额（USD）");
            headerNames.add("危险运输品");
            headerNames.add("海关编码");
            headerNames.add("开发员（输入子账号姓名或名称）");

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
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(report.getWeight());
                row.createCell(7).setCellValue(report.getPrice());
                row.createCell(8).setCellValue(report.getCreator());
                row.createCell(9).setCellValue("0");
                row.createCell(10).setCellValue("0");
                row.createCell(11).setCellValue("0");
                row.createCell(12).setCellValue(report.getSourceUrl());
                row.createCell(13).setCellValue("");
                row.createCell(14).setCellValue(report.getNameEnBg());
                row.createCell(15).setCellValue(report.getNameCnBg());
                row.createCell(16).setCellValue(report.getWeightBg());
                row.createCell(17).setCellValue(report.getPriceBg());
                String dangours=  report.getDangerDesBg();
                switch (dangours){
                    case "无":
                        row.createCell(18).setCellValue(0);
                        break;
                     case "含电":
                        row.createCell(18).setCellValue(1);
                        break;
                     case "液体":
                        row.createCell(18).setCellValue(2);
                        break;
                     case "粉末":
                        row.createCell(18).setCellValue(3);
                        break;
                     case "纯电":
                        row.createCell(18).setCellValue(4);
                        break;
                     case "膏体":
                        row.createCell(18).setCellValue(5);
                        break;
                     case "带磁":
                        row.createCell(18).setCellValue(6);
                        break;

                }

                row.createCell(19).setCellValue(report.getHgbmBg());
                row.createCell(20).setCellValue("");
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

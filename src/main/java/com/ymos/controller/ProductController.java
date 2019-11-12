package com.ymos.controller;


import com.ymos.biz.ProductService;
import com.ymos.common.Constants;
import com.ymos.common.LoginContext;
import com.ymos.entity.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pro")
public class ProductController extends CUDController<Product, ProductQuery,ProductForm, ProductService> {


    @Value("#{prop.uploadFile}")
    private String uploadFile;
    @Value("#{prop.filePath}")//图片上传服务器路径
    private String filePath;

    private ProductService productService;



    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
        this.service = productService;
    }

    public ProductController() {
        super("pro");
    }

    @Override
    protected void innerSave(ProductForm form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = new Product();
            form.setCreateDate(product.getCreateDate());
            form.setCreator(product.getCreator());
            form.setCus_ch_name(product.getCus_ch_name());
            form.setCus_en_name(product.getCus_en_name());
            form.setCus_weight(product.getCus_weight());
            form.setCus_price(product.getCus_price());
            form.setPresale_price(product.getPresale_price());
            form.setPro_ch_name(product.getPro_ch_name());
            form.setPro_en_name(product.getPro_en_name());
            form.setPro_list(product.getPro_list());
            form.setPro_purchase_price(product.getPro_purchase_price());
            form.setRemark(product.getRemark());
            form.setSoureId(product.getSoureId());
            form.setPro_url(product.getPro_url());
            form.setStatus(product.getStatus());
            form.setUrl(product.getUrl());
            form.setUrl2(product.getUrl2());
            form.setUrl3(product.getUrl3());
            form.setPro_url(product.getPro_url());
            form.setDateTime(product.getDateTime());
            this.service.saveOrUpdate(form.toObj());
        } catch (Exception e) {
            errors.addError(new ObjectError("error", "操作异常"));
        }

    }




    @Override
    protected boolean preList(ProductQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }

    /**
     * 新增产品数据
     */
    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<Product> create(Product product, MultipartFile[] file, HttpServletRequest request, HttpSession session) {
        User user=LoginContext.getUser(session);
        DecimalFormat df=new DecimalFormat("000000");
        //String date= String.valueOf(new Date());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        product.setDateTime(new Date());
        int maxId = productService.queryMaxId();
        //System.out.println(maxId + "maxId");
        //System.out.println(request.getParameter("pro_list"));
        int num = (int) ((Math.random() * 9 + 1) * 10000);
        product.setSoureId((num + "" + maxId));
        String uuid=product.getPro_list();
        String uuidAfter=product.getPro_list();
        uuidAfter=uuidAfter.substring(0,uuidAfter.length()-2);
        uuid= uuid.substring(uuid.length()-2,uuid.length());
        product.setPro_list(uuidAfter);
        product.setStatus(3);
        product.setSpu(uuid+df.format(maxId));
        product.setCreator(user.getUsername());

        try {
            if (file != null && file.length != 0) {
                saveFiles(request, product, file);//文件上传
            }

            productService.create(product);
            return new Result<Product>().setData(product).setFlag(true);
        } catch (Exception e) {
            return new Result<Product>().setFlag(false);
        }

    }

    /**
     * 文件上传
     */
    public void saveFiles(HttpServletRequest request, Product product, MultipartFile[] file) {
        try {
            String temp = "";
            for (int i = 0; i < file.length; i++) {
                String filename = file[i].getOriginalFilename();
                //System.out.println(filename + "文件原始名");
                String uploadPath = uploadFile + "/" + product.getSoureId() + i + filename.substring(filename.lastIndexOf("."));
                String filePaths = filePath + "/" + product.getSoureId() + i + filename.substring(filename.lastIndexOf("."));
                //System.out.println("uploadPath: " + uploadPath + ">>>>>>>" + filePath);
                temp += filePaths;
                File file1 = new File(uploadPath);
                if (!file1.exists()) {
                    file1.mkdirs();
                } else if (file1.exists()) {
                    Boolean result = file1.delete();
                }
                file[i].transferTo(file1);
            }
            product.setPro_url(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改产品数据
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Product> update(HttpServletRequest request, HttpServletResponse response, Product product, MultipartFile[] file) {
        try {
            String uuid = product.getPro_list();
            String uuidAfter = product.getPro_list();
            uuidAfter = uuidAfter.substring(0, uuidAfter.length() - 2);
            uuid = uuid.substring(uuid.length() - 2, uuid.length());
            product.setPro_list(uuidAfter);
            product.setDateTime(new Date());
            int maxId = productService.queryMaxId();

            if (file != null) {
                saveFiles(request, product, file);
            }

            productService.modify(product);

            return new Result<Product>().setData(product).setFlag(true);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<Product>().setData(product).setFlag(false);
        }

    }

    /**
     * Excel表格导出方法
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(ProductReport productReport, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (productReport.getChannelId() == null && productReport.getCreateDate() == null ||
                    productReport.getOperator() == null && productReport.getOperator() == "" ||
                    productReport.getEndDate() == null && productReport.getEndDate() == "" ||
                    productReport.getCountry() == null && productReport.getCountry() == "" ||
                    productReport.getBeginDate() == null && productReport.getBeginDate() == "") {
                productReport.setBeginDate(Constants.yyyyMMdd.format(new Date()));
            }
            String filename = productReport.getTitle() + Constants.yyyyMMdd.format(new Date()) + ".xls";
//            filename=new String(filename.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            exportExcel(response.getOutputStream(), productReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcel(ServletOutputStream outputStream, ProductReport productReport) throws IOException {
        Workbook wk = new HSSFWorkbook();
        String count = null;
        try {
            //List<Review> list = reviewService.AllCountry();
            List<ProductReport> cpsChannelList = productService.queryExcelData(productReport);
            System.out.println(cpsChannelList.size());

            //创建工作表
            Sheet sheet = wk.createSheet(productReport.getTitle());
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
            for (ProductReport report : cpsChannelList) {
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
            //wk.close();
        }
    }



}

package com.ymos.controller;


import com.ymos.common.Constants;
import com.ymos.common.LoginContext;
import com.ymos.entity.*;
import com.ymos.biz.ReviewService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
@RequestMapping(value = "/rev")
public class ReviewController extends CUDController<Review, ReviewQuery, ReviewForm, ReviewService> {


     ReviewService reviewService;

    @Value("#{prop.uploadFile}")
    private String uploadFile;
    @Value("#{prop.filePath}")//图片上传服务器路径
    private String filePath;



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

        try {
            DecimalFormat df=new DecimalFormat("000000");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Product product = new Product();
            form.setDateTime(sdf.format(date));
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

            this.service.saveOrUpdate(form.toObj());
        } catch (Exception e) {
            errors.addError(new ObjectError("error", "操作异常"));
        }

    }

    @Override
    protected boolean preList(ReviewQuery query, Paginator paginator, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        paginator.setNeedTotal(true);
        return super.preList(query, paginator, model, request, response);
    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public Result<Product> update(HttpServletRequest request, HttpServletResponse response, Product product, MultipartFile [] file,HttpSession session) {
        String sp = product.getId();
        User user= LoginContext.getUser(session);
        String creator=user.getUsername();
        DecimalFormat df=new DecimalFormat("000000");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        product.setDateTime(sdf.format(date));
        int maxId = reviewService.queryMaxId();
        int num = (int) ((Math.random() * 9 + 1) * 10000);
        product.setSoureId((num + "" + maxId));
        String uuid=product.getPro_list();
        String uuidAfter=product.getPro_list();
        uuidAfter=uuidAfter.substring(0,uuidAfter.length()-2);
        uuid= uuid.substring(uuid.length()-2,uuid.length());
        product.setPro_list(uuidAfter);
        product.setCreator(creator);
        product.setStatus(3);
        try {
            if (file != null && file.length != 0) {
                saveFiles(request, product, file);//文件上传
            }

            reviewService.update(product);
            return new Result<Product>().setData(product).setFlag(true);
        } catch (Exception e) {
            return new Result<Product>().setFlag(false);
        }
    }




    /**
     * 新增产品数据
     */
    @ResponseBody
    @RequestMapping(value = "/create1",method = RequestMethod.POST)
    public Result<Product> create(Product product, MultipartFile[] file, HttpServletRequest request, HttpSession session) {

        User user= LoginContext.getUser(session);
        String creator=user.getUsername();
        DecimalFormat df=new DecimalFormat("000000");
        //String date= String.valueOf(new Date());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        product.setDateTime(sdf.format(date));
        int maxId = reviewService.queryMaxId();
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
        product.setCreator(creator);

        try {
            if (file != null && file.length != 0) {
                saveFiles(request, product, file);//文件上传
            }

            reviewService.create(product);
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
                System.out.println("uploadPath: " + uploadPath + ">>>>>>>" + filePath);
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

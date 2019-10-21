package com.ymos.controller;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.ymos.biz.OrderService;
import com.ymos.common.Constants;
import com.ymos.entity.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController extends CUDController<Order, OrderQuery, OrderForm, OrderService> {


    OrderService orderService;

    @Value("#{prop.uploadFiles}")//文件上传服务器路径
    private String uploadFiles;
    @Value("#{prop.filePaths}")//文件获取前缀
    private String filePaths;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
        this.service = orderService;
    }

    public OrderController() {
        super("order");
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
     * 文件上传
     */
    public void saveFiles(HttpServletRequest request, MultipartFile file, String path) {
        try {
            String temp = "";
            //for (int i=0;i<file.length;i++) {
            String filename = file.getOriginalFilename();
            String uploadPath =uploadFiles + "/" + filename;
            path =filePaths + "/" + filename;
            //if(file.length>0){
            //    temp+=filePaths+",";
            //}else {
            //    temp+=filePaths;
            //}

            File file1 = new File(uploadPath);
            if (!file1.exists()) {
                file1.mkdirs();
            } else if (file1.exists()) {
                Boolean result = file1.delete();

            }
            file.transferTo(file1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Excel导入
     * @param file
     * @param request
     * @param response
     * @param path
     * @param order
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/import")
    public Result<Order> importUsers(MultipartFile file, HttpServletRequest request, HttpServletResponse response, String path, Order order) throws IOException {

        //返回值显示文件导入结果
        Map<String, Integer> maps = new HashMap<>();
        Integer sum = 0; ///总条数
        Integer success = 0;//成功数
        Integer fail = 0;//失败数
        Integer flag = 1;//判断是否有有用的表
        jxl.Workbook workbook = null;
        order = new Order();
        try {
            System.out.println("path"+path);
            if (file != null) {
                //由于项目中使用的springMvc获取的文件类型为MultipartFile类型
                //Workbook.getWorkbook获取的是file类型,尝试强转获取的文件类型为File,运行时报错
                //此处使用项目中已经有的方法将获取的文件上传到服务器创建的文件中,在方法的最后判断文件是否存在,然后进行删除
                saveFiles(request, file, path);
                request.getContextPath();
                //文件的名字
                String fileName = file.getOriginalFilename();

                //通过当前类获取文件的绝对路径
                String path1 =uploadFiles + "/" + fileName;
                //URL url=new URL(path1);
                //URLConnection  conn = url.openConnection();
                //BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                        //String ous=path1.replaceAll("\\\\","/");
                //获取excel文件
                workbook = Workbook.getWorkbook(new File(path1));
                int oSheet = 100;
                //获取表名所对应的sheet,workbook.getNumberOfSheets()获取excel中有几张表
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    oSheet = i;
                }
                //订单
                if (oSheet != 100) {
                    Sheet sheet3 = workbook.getSheet(oSheet);
                    //订单信息list(每条数据添加进去,最后实现批量添加数据)
                    List<Order> orders = new ArrayList<>();
                    int lastRowNum = sheet3.getRows() - 1;
                    //循环行sheet2.getRows(),导入的excel信息是直接从第一行开始的
                    for (int i = 1; i < sheet3.getRows(); i++) {
                        boolean ssheet = true;

                        //信息数
                        sum++;
                        int cou = sheet3.getColumns();
                        //循环列,从0开始,本次导入的excel格式是从第二列开始的
                        for (int j = 0; j < sheet3.getColumns(); j++) {
                            //当前行列对应的单元格
                            Cell cell = sheet3.getCell(j, i);
                            //获取行列所对应的内容
                            String contents = cell.getContents();
                            //当前行数据校验通过,导入数据到对象
                            if (ssheet) {
                                //满足条件的条数++
                                success++;

                                if (j == 0) {
                                    //包裹号
                                    order.setBgh(contents);
                                }
                                if (j == 1) {
                                    //订单号
                                    order.setOrderId(contents);
                                }
                                if (j == 2) {
                                    //交易号
                                    order.setJyh(contents);
                                }
                                if (j == 3) {
                                    //订单状态
                                    order.setOrderStatus(contents);
                                }
                                if (j == 4) {
                                    //平台渠道
                                    order.setPtqd(contents);
                                }
                                if (j == 5) {
                                    //店铺账号
                                    order.setDpzh(contents);
                                }
                                if (j == 6) {
                                    //订单备注
                                    order.setOrderRamker(contents);
                                }
                                if (j == 7) {
                                    //拣货备注
                                    order.setJhRamker(contents);
                                }
                                if (j == 8) {
                                    //客服备注
                                    order.setKfRamker(contents);
                                }
                                if (j == 9) {
                                    //退款理由
                                    order.setTkly(contents);
                                }
                                if (j == 10) {
                                    //下单时间
                                    String str = contents;
                                    if(str=="" || str==null){
                                        order.setXdsj(null);
                                    }else {
                                        order.setXdsj(str);
                                    }
                                }
                                if (j == 11) {
                                    //付款时间
                                    String str = contents;
                                    if(str==""|| str==null){
                                        order.setFksj(null);
                                    }else {
                                        order.setFksj(str);
                                    }
                                }
                                if (j == 12) {
                                    //提交时间
                                    String str = contents;
                                    if(str==""|| str==null){
                                        order.setTjsj(null);
                                    }else {
                                        order.setTjsj(str);
                                    }
                                }
                                if (j == 13) {
                                    //发货时间
                                    String str = contents;
                                    if(str==""|| str==null){
                                        order.setFhsj(null);
                                    }else {
                                        order.setFhsj(str);
                                    }
                                }
                                if (j == 14) {
                                    //退款时间
                                    String str = contents;
                                    if(str==""||str==null){
                                        order.setTksj(null);
                                    }else {
                                        order.setTksj(str);
                                    }
                                }
                                if (j == 15) {
                                    //面单打印时间
                                    String str = contents;

                                    if(str==""||str==null){
                                        order.setMddysj(null);
                                    }else if(str.length()>20){
                                        str =str.substring(0,19);
                                        order.setMddysj(str);
                                    }else {
                                        order.setMddysj(str);
                                    }

                                }
                                if (j == 16) {
                                    //拣货单打印时间
                                    String str = contents;
                                    if(str==""||str==null){
                                        order.setJhdysj(null);
                                    }else {
                                        order.setJhdysj(str);
                                    }
                                }
                                if (j == 17) {
                                    //付款方式
                                    order.setFkfs(contents);
                                }
                                if (j == 18) {
                                    //币种缩写
                                    order.setBzsx(contents);
                                }
                                if (j == 19) {
                                    //订单金额
                                    String str = contents;
                                    if (str == "") {
                                        order.setOrderMoney(null);
                                    } else {
                                        //String stre = str.replaceAll("\\n*", " ");
                                        order.setOrderMoney(str);

                                    }

                                }
                                if (j == 20) {
                                    //买家支付运费
                                    String str = contents;
                                    if (str == "") {
                                        order.setMjzfyf(null);
                                    } else {
                                        //String stre = str.replaceAll("\\n*", " ");
                                        order.setMjzfyf(str);
                                    }

                                }
                                if (j == 21) {
                                    //退款金额
                                    String str = contents;
                                    if (str == "") {
                                        order.setTkMoney(null);
                                    } else {
                                        String stre = str.replaceAll("\\n*", " ");
                                        order.setTkMoney(stre);
                                    }

                                }
                                if (j == 22) {
                                    //预估利润
                                    order.setYgly(contents);
                                }
                                if (j == 23) {
                                    //成本利润率
                                    order.setCbly(contents);
                                }

                                if (j == 24) {
                                    //销售利润率
                                    order.setXsly(contents);
                                }
                                if (j == 25) {
                                    //预估运费
                                    String str = contents;
                                    String strs;

                                    if (str == "") {
                                        order.setYgfy(null);
                                    } else {
                                        String stre = str.replaceAll("\\n*", "");
                                        order.setYgfy(stre);
                                    }
                                }
                                if (j == 26) {
                                    //sku
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setSku(stre);
                                }
                                if (j == 27) {
                                    //产品ID
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setProId(stre);
                                }
                                if (j == 28) {
                                    //产品名称
                                    String str = contents;
                                    EmojiConverter emojiConverter = EmojiConverter.getInstance();
                                    str= emojiConverter.toAlias(str);//将聊天内容进行转义
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setProName(stre);
                                }
                                if (j == 29) {
                                    //产品售价
                                    String str = contents;
                                    //String stre = str.replaceAll("\\t", " ");
                                    if (str == "") {
                                        order.setProPrice(null);
                                    } else {
                                        order.setProPrice(str);
                                    }

                                }
                                if (j == 30) {
                                    //产品数量
                                    String str = contents;
                                    if (str == "") {
                                        order.setProNum(0);
                                    } else {
                                        order.setProNum(Integer.parseInt(contents));
                                    }

                                }
                                if (j == 31) {
                                    //产品规格
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setProGuige(stre);
                                }
                                if (j == 32) {
                                    //图片网址
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");

                                    order.setImgURL(stre);
                                }
                                if (j == 33) {
                                    //来源URL
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setLyURL(stre);
                                }
                                if (j == 34) {
                                    //销售链接
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setXslj(stre);

                                }
                                if (j == 35) {
                                    //多品名
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setDpm(stre);
                                }
                                if (j == 36) {
                                    //商品SKU
                                    String str = contents;
                                    //String stre = str.replaceAll("\\n*", "");
                                    order.setSpSKU(str);
                                }
                                if (j == 37) {
                                    //商品编码
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setSpbm(stre);
                                }
                                if (j == 38) {
                                    //商品名称
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setSpmc(stre);
                                }
                                if (j == 39) {
                                    //库存量
                                    String str = contents;

                                    if (str == "") {
                                        order.setKcl(0);
                                    } else {
                                        String stre = str.replaceAll("\\n*", "");
                                        order.setKcl(Integer.parseInt(stre));
                                    }

                                }
                                if (j == 40) {
                                    String str = contents;
                                    if (str == "") {

                                        order.setSpcgj(null);
                                    } else {
                                        String stre = str.replaceAll("\\n*", "");
                                        order.setSpcgj(stre);
                                    }

                                }
                                if (j == 41) {
                                    String str = contents;
                                    if (str == "") {

                                        order.setKcjg(null);
                                    } else {
                                        String stre = str.replaceAll("\\n*", "");
                                        order.setKcjg(stre);
                                    }

                                }
                                if (j == 42) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setWxysp(stre);
                                }
                                if (j == 43) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setFhck(stre);
                                }
                                if (j == 44) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setHjw(stre);
                                }
                                if (j == 45) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setMjzh(stre);
                                }
                                if (j == 46) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setMjname(stre);
                                }
                                if (j == 47) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setMjEmail(stre);
                                }
                                if (j == 48) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setShrname(stre);
                                }
                                if (j == 49) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setShrcompany(stre);
                                }
                                if (j == 50) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setShrsh(stre);
                                }
                                if (j == 51) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setShrmph(stre);
                                }
                                if (j == 52) {
                                    String str = contents;
                                    String cons;
                                    cons = str.replace(",", "/");
                                    order.setXxaddress(cons);
                                }
                                if (j == 53) {
                                    order.setAddresOne(contents);
                                }
                                if (j == 54) {
                                    order.setAddressTwo(contents);
                                }
                                if (j == 55) {
                                    order.setAddOneTwo(contents);
                                }
                                if (j == 56) {
                                    order.setShrCity(contents);
                                }
                                if (j == 57) {
                                    order.setShrShen(contents);
                                }
                                if (j == 58) {
                                    order.setCode(contents);
                                }
                                if (j == 59) {
                                    order.setShrCountry(contents);
                                }
                                if (j == 60) {
                                    order.setChCountry(contents);
                                }
                                if (j == 61) {
                                    order.setCountryS(contents);
                                }
                                if (j == 62) {
                                    order.setShrTel(contents);
                                }
                                if (j == 63) {
                                    order.setShrIphone(contents);
                                }
                                if (j == 64) {
                                    order.setMjzdwl(contents);
                                }
                                if (j == 65) {
                                    order.setWlfs(contents);
                                }
                                if (j == 66) {
                                    order.setYdh(contents);
                                }
                                if (j == 67) {
                                    String str = contents;
                                    if (str == "") {

                                        order.setCzzl(null);
                                    } else {
                                        String stre = str.replaceAll("\\n*", "");
                                        order.setCzzl(stre);
                                    }

                                }
                                if (j == 68) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setChbgName(stre);
                                }
                                if (j == 69) {
                                    String str = contents;
                                    String stre = str.replaceAll("\\n*", "");
                                    order.setEnbgName(stre);
                                }
                                if (j == 70) {
                                    String str = contents;
                                    if (str == "") {

                                        order.setSbPrice(null);
                                    } else {
                                        //String stre = str.replaceAll("\\n*", "");
                                        order.setSbPrice(str);
                                    }

                                }
                                if (j == 71) {
                                    String str = contents;
                                    if (str == "") {
                                        order.setBgWidth(0);
                                    } else {
                                        order.setBgWidth(Integer.parseInt(contents));
                                    }
                                }
                            } else {
                                //数据校验未通过
                                fail++;
                            }

                        }
                        //此处orders 数据添加...
                        orders.add(order);
                        //批量添加订单数据
                        if (orders.size() > 0) {
                            orderService.create(orders);

                        }
                    }

                    //判断是否有有用的表
                    if (oSheet == 100) {
                        flag = 0;
                        return new Result<Order>().setData(order).setFlag(false);
                    }
                }
                if (file == null) {
                    //没有文件
                    maps.put("文件", 0);
                    return new Result<Order>().setData(order).setFlag(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<Order>().setData(order).setFlag(false);
        } finally {
            workbook.close();
            File img = new File(this.getClass().getClassLoader().getResource("../../").getPath() + path + "/" + file.getOriginalFilename());
            //判断文件是否存在,删除文件
            if (img.exists()) {
                img.delete();
            }
        }
        maps.put("flag", flag);//1:有有用的表;0:无有用的表
        maps.put("sum", sum);//总数据条数
        maps.put("success", success);//能成功添加的数据条数
        maps.put("fail", fail);//失败的数据条数
        return new Result<Order>().setData(order).setFlag(true);
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
            List<OrderReport> cpsChannelList = orderService.exportExcel(orderReport);
            System.out.println(cpsChannelList.size());

            //创建工作表
            org.apache.poi.ss.usermodel.Sheet sheet = wk.createSheet(orderReport.getTitle());
            //创建0行，表头
            Row row = sheet.createRow(0);
            List<String> headerNames = new ArrayList<>();
            List<Integer> columnsWidths = new ArrayList<>();
            headerNames.add("包裹号");
            headerNames.add("SKU");
            headerNames.add("产品规格");
            headerNames.add("币种");
            headerNames.add("金额");
            headerNames.add("预估利润");
            headerNames.add("收件人");
            headerNames.add("国家");
            headerNames.add("买家指定");
            headerNames.add("订单号");
            headerNames.add("运单号");
            headerNames.add("时间");
            headerNames.add("状态");

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
                row.createCell(0).setCellValue(report.getBgh());
                row.createCell(1).setCellValue(report.getSku());
                row.createCell(2).setCellValue(report.getProGuige());
                row.createCell(3).setCellValue(report.getBzsx());
                row.createCell(4).setCellValue(report.getOrderMoney());
                row.createCell(5).setCellValue(report.getYgly());
                row.createCell(6).setCellValue(report.getShrname());
                row.createCell(7).setCellValue(report.getChCountry());
                row.createCell(8).setCellValue(report.getMjzdwl());
                row.createCell(9).setCellValue(report.getOrderId());
                row.createCell(10).setCellValue(report.getYdh());
                row.createCell(11).setCellValue(report.getFksj());
                row.createCell(12).setCellValue(report.getOrderStatus());
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

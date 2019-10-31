package com.ymos.controller;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.ymos.biz.OrderService;
import com.ymos.common.Constants;
import com.ymos.common.ImportExcelUtil;
import com.ymos.entity.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
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
import java.io.FileInputStream;
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
            String uploadPath = uploadFiles + "/" + filename;
            path = filePaths + "/" + filename;
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
     *
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
        WorkbookSettings settings = new WorkbookSettings();
        settings.setGCDisabled(true);
        //Workbook workbook = Workbook.getWorkbook(new FileInputStream(""), settings);
        jxl.Workbook workbook = null;

        try {
            System.out.println("path" + path);
            if (file != null) {
                //由于项目中使用的springMvc获取的文件类型为MultipartFile类型
                //Workbook.getWorkbook获取的是file类型,尝试强转获取的文件类型为File,运行时报错
                //此处使用项目中已经有的方法将获取的文件上传到服务器创建的文件中,在方法的最后判断文件是否存在,然后进行删除
                saveFiles(request, file, path);
                request.getContextPath();
                //文件的名字
                String fileName = file.getOriginalFilename();

                //通过当前类获取文件的绝对路径
                String path1 = uploadFiles + "/" + fileName;

                //获取excel文件
                workbook = Workbook.getWorkbook(new File(path1), settings);
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
                        if (ssheet) {
                            //满足条件的条数++
                            success++;

                            Cell[] cells = sheet3.getRow(i);
                            order = new Order();
                            order.setBgh(cells[0].getContents());//包裹号
                            order.setOrderId(cells[1].getContents());//订单号
                            order.setJyh(cells[2].getContents());//交易号
                            order.setOrderStatus(cells[3].getContents());//订单状态
                            order.setPtqd(cells[4].getContents());//平台渠道
                            order.setDpzh(cells[5].getContents());//店铺账号
                            order.setOrderRamker(cells[6].getContents());//订单备注
                            order.setJhRamker(cells[7].getContents());//拣货备注
                            order.setKfRamker(cells[8].getContents());//客服备注
                            order.setTkly(cells[9].getContents());//退款理由
                            if(cells[10].getContents()==""||cells[10].getContents()==null){
                                order.setXdsj(null);
                            }else{
                                order.setXdsj(cells[10].getContents());//下单时间
                            }
                            if(cells[11].getContents()==""||cells[11].getContents()==null){
                                order.setFksj(null);
                            }else {
                                order.setFksj(cells[11].getContents());//付款时间
                            }
                            if(cells[12].getContents()==""||cells[12].getContents()==null){
                                order.setTjsj(null);
                            }else {
                                order.setTjsj(cells[12].getContents());//提交时间
                            }
                            if(cells[13].getContents()==""||cells[13].getContents()==null){
                                order.setFhsj(null);
                            }else {
                                order.setFhsj(cells[13].getContents());//发货时间
                            }
                            if(cells[14].getContents()==""||cells[14].getContents()==null){
                                order.setTksj(null);
                            }else {
                                order.setTksj(cells[14].getContents());//退款时间
                            }
                            if(cells[15].getContents()==""||cells[15].getContents()==null){
                                order.setMddysj(null);
                            }else if(cells[15].getContents().length()>20){
                                order.setMddysj(cells[15].getContents().substring(0,19));
                            }else {
                                order.setMddysj(cells[15].getContents());//面单打印时间
                            }
                            if(cells[16].getContents()==""||cells[16].getContents()==null){
                                order.setJhdysj(null);
                            }else {
                                order.setJhdysj(cells[16].getContents());//拣货单打印时间
                            }
                            order.setFkfs(cells[17].getContents());//付款方式
                            order.setBzsx(cells[18].getContents());//币种缩写
                            order.setOrderMoney(cells[19].getContents());//订单金额
                            order.setMjzfyf(cells[20].getContents());//买家支付运费
                            order.setTkMoney(cells[21].getContents());//退款金额
                            order.setYgly(cells[22].getContents());//预估利润
                            order.setCbly(cells[23].getContents());//成本利润率
                            order.setXsly(cells[24].getContents());//销售利润率
                            order.setYgfy(getTrim(cells[25].getContents()));//预估运费
                            order.setSku(getTrim(cells[26].getContents()));//sku
                            order.setProId(getTrim(cells[27].getContents()));//产品ID
                            String em=cells[28].getContents();
                            EmojiConverter emojiConverter = EmojiConverter.getInstance();
                            em= emojiConverter.toAlias(em);//将聊天内容进行转义
                            order.setProName(getTrim(em));//产品名称
                            order.setProPrice(cells[29].getContents());//产品售价
                            order.setProNum(Integer.parseInt(cells[30].getContents()));//产品数量
                            order.setProGuige(getTrim(cells[31].getContents()));//产品规格
                            order.setImgURL(getTrim(cells[32].getContents())); //图片网址
                            order.setLyURL(getTrim(cells[33].getContents()));//来源URL
                            order.setXslj(getTrim(cells[34].getContents()));//销售链接
                            order.setDpm(getTrim(cells[35].getContents()));//多品名
                            order.setSpSKU(cells[36].getContents());//商品SKU
                            order.setSpbm(getTrim(cells[37].getContents()));//商品编码
                            order.setSpmc(getTrim(cells[38].getContents()));//商品名称
                            order.setKcl(getTrim(cells[39].getContents())); //库存量
                            order.setSpcgj(getTrim(cells[40].getContents()));
                            order.setKcjg(getTrim(cells[41].getContents()));
                            order.setWxysp(getTrim(cells[42].getContents()));
                            order.setFhck(getTrim(cells[43].getContents()));
                            order.setHjw(getTrim(cells[44].getContents()));
                            order.setMjzh(getTrim(cells[45].getContents()));
                            order.setMjname(getTrim(cells[46].getContents()));
                            order.setMjEmail(getTrim(cells[47].getContents()));
                            order.setShrname(getTrim(cells[48].getContents()));
                            order.setShrcompany(getTrim(cells[49].getContents()));
                            order.setShrsh(getTrim(cells[50].getContents()));
                            order.setShrmph(getTrim(cells[51].getContents()));
                            order.setXxaddress(getTrim1(cells[52].getContents()));
                            order.setAddresOne(cells[53].getContents());
                            order.setAddressTwo(cells[54].getContents());
                            order.setAddOneTwo(cells[55].getContents());
                            order.setShrCity(cells[56].getContents());
                            order.setShrShen(cells[57].getContents());
                            order.setCode(cells[58].getContents());
                            order.setShrCountry(cells[59].getContents());
                            order.setChCountry(cells[60].getContents());
                            order.setCountryS(cells[61].getContents());
                            order.setShrTel(cells[62].getContents());
                            order.setShrIphone(cells[63].getContents());
                            order.setMjzdwl(cells[64].getContents());
                            order.setWlfs(cells[65].getContents());
                            order.setYdh(cells[66].getContents());
                            order.setCzzl(cells[67].getContents());
                            order.setChbgName(getTrim(cells[68].getContents()));
                            order.setEnbgName(getTrim(cells[69].getContents()));
                            order.setSbPrice(cells[70].getContents());
                            order.setBgWidth(cells[71].getContents());

                        } else {
                            //数据校验未通过
                            fail++;
                        }
                        //此处orders 数据添加...
                        orders.add(order);
                        //批量添加订单数据
                    }
                    System.out.println(orders.size()+"???????????????");


                    if (orders.size() > 0) {
                            orderService.create(orders);

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
     * 去换行
     * @param controns
     * @return
     */
    public String getTrim(String controns){
        String contents=controns.replaceAll("\\n*", "");
        return contents;
    }

    public String getTrim1(String controns){
        String contents=controns.replace(",", "/");
        return contents;
    }

    //public String getTime(String controns){
    //    if(controns=="" || controns==null){
    //
    //    }else {
    //
    //    }
    //    return  controns;
    //
    //}




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
            headerNames.add("订单号");
            headerNames.add("店铺账号");
            headerNames.add("付款时间");
            headerNames.add("发货时间");
            headerNames.add("退款时间");
            headerNames.add("订单金额");
            headerNames.add("产品售价");
            headerNames.add("产品数量");
            headerNames.add("SKU");
            headerNames.add("买家账号");
            headerNames.add("买家姓名");
            headerNames.add("买家Email");
            headerNames.add("物流方式");
            headerNames.add("运单号");
            headerNames.add("称重重量");
            headerNames.add("拣货备注");
            headerNames.add("客服备注");

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
                row.createCell(2).setCellValue(report.getDpzh());
                row.createCell(3).setCellValue(report.getFksj());
                row.createCell(4).setCellValue(report.getFhsj());
                row.createCell(5).setCellValue(report.getTksj());
                row.createCell(6).setCellValue(report.getOrderMoney());
                row.createCell(7).setCellValue(report.getProPrice());
                row.createCell(8).setCellValue(report.getProNum());
                row.createCell(9).setCellValue(report.getSku());
                row.createCell(10).setCellValue(report.getMjzh());
                row.createCell(11).setCellValue(report.getMjname());
                row.createCell(12).setCellValue(report.getMjEmail());
                row.createCell(13).setCellValue(report.getWlfs());
                row.createCell(14).setCellValue(report.getYdh());
                row.createCell(15).setCellValue(report.getCzzl());
                row.createCell(16).setCellValue(report.getJhRamker());
                row.createCell(17).setCellValue(report.getKfRamker());
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

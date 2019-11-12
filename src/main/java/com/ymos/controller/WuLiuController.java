package com.ymos.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ymos.biz.OrderService;
import com.ymos.biz.WuLiuService;
import com.ymos.biz.WuLiuServiceImpl;
import com.ymos.common.Tracker;
import com.ymos.entity.*;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.aspectj.weaver.ast.Or;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;

@Component
@Controller
@RequestMapping("/wuliu")
public class WuLiuController extends CUDController<Order, OrderQuery, OrderForm, WuLiuService> {

    static WuLiuService wuLiuService;

    @Autowired
    public void setWuLiuService(WuLiuService wuLiuService) {
        this.wuLiuService = wuLiuService;
        this.service = wuLiuService;
    }

    public WuLiuController() {
        super("wuliu");
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

    //@Scheduled(cron = "0 0 0-3 * * ? ")
    @ResponseBody
    @RequestMapping(value = "/showStau")
    @Scheduled(cron = "0 0 0/3 * * ? ")
    public List<Order> AllCount() {
        List<Order> list = wuLiuService.AllCount();
        System.out.println(list.size());
        Order order = new Order();

        for (int i = 0; i < list.size(); i++) {
            String status = getWuliuInfo(list.get(i).getYdh());
            String id=list.get(i).getId();
            String object = JSONObject.valueToString(status);
            com.alibaba.fastjson.JSONObject ss = JSON.parseObject(status);

            String sss = JSONObject.valueToString(ss.getJSONObject("data"));
            com.alibaba.fastjson.JSONObject sss1 = JSON.parseObject(sss);
            Object sta = sss1.get("status");
            Object days=sss1.get("itemTimeLength");
            //System.out.println("days:"+days);
            Object wuliu=sss1.get("origin_info");
            System.out.println("wuliu"+wuliu);
            String obj1= JSONObject.valueToString(wuliu);
            com.alibaba.fastjson.JSONObject s1=JSON.parseObject(obj1);
            JSONArray array=s1.getJSONArray("trackinfo");
            Object track=array.getJSONObject(0).get("StatusDescription");
            Object times=array.getJSONObject(0).get("Date");
            //System.out.println(array.getJSONObject(0).get("StatusDescription"));

            switch (sta.toString()) {
                case "pending":
                    order.setOrderStatus("查询中");
                    break;
                case "notfound":
                    order.setOrderStatus("查询不到");
                    break;
                case "transit":
                    order.setOrderStatus("运输途中");
                    break;
                case "pickup":
                    order.setOrderStatus("到达待取");
                    break;
                case "delivered":
                    order.setOrderStatus("成功签收");
                    break;
                case "undelivered":
                    order.setOrderStatus("投递失败");
                    break;
                case "exception":
                    order.setOrderStatus("可能异常");
                    break;
                case "expired":
                    order.setOrderStatus("运输过久");
                    break;
            }
            order.setId(id);
            order.setItemTimeLength(days.toString());
            order.setWuliu("时间："+times+"状态"+track.toString());
            //System.out.println("111" + sss1.get("status"));
            int count = wuLiuService.update(order);

        }
        return list;
    }


    /**
     * 查询运输商简码
     *
     * @param ydh
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wuInfo/{ydh}",produces = {"text/html; charset=utf-8"})
    public static String getWuliuInfo(@PathVariable String ydh) {

        String urlStr = null;
        String requestData = "{\"tracking_number\":\"" + ydh + "\"}";
        String result = null;
        String jsonDate = null;
        String su = null;
        String queryCon =null;
        try {
            result = new Tracker().orderOnlineByJson(requestData, urlStr, "carriers/detect");
            //System.out.println("result>>>>>> " + result);
            su = result.substring(result.lastIndexOf(":") + 2);
            //System.out.println("su:>>>>>>>" + su);
            if (su.length() != 0) {
                su = su.substring(0, su.length() - 4);
            }
            requestData = "{\"tracking_number\": \"" + ydh + "\",\"carrier_code\":\"" + su + "\"}";

            result = new Tracker().orderOnlineByJson(requestData, urlStr, "post");

            //System.out.println(su);
            //System.out.println(result);
            jsonDate = getShowCode(su, ydh);
            //jsonDate = new String(jsonDate.getBytes("GBK"), "utf-8");
            //System.out.println(jsonDate);
            //HttpServletRequest request=null;
            // queryCon = request.getParameter(jsonDate);
            //if(queryCon != null && queryCon != ""){
            //    queryCon= URLDecoder.decode(queryCon,"utf-8");
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("result=======" + result);
        return jsonDate;
    }

    /**
     * 物流详细信息
     *
     * @param code
     * @param ydh
     * @return
     */
    public static String getShowCode(String code, String ydh) {
        String urlStr = "/" + code + "/" + ydh + "/ch";
        String requestData = null;
        String result = null;
        try {
            result = new Tracker().orderOnlineByJson(requestData, urlStr, "codeNumberGet");
            //System.out.println("物流详细信息" + result);
            //System.out.println("result=======" + result);
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getAllWuli(String ydh) {
        String lang="cn";
        String urlStr = null;
        String requestData = null;
        String result = null;
        try {
            String code = getWuliuInfo(ydh);
            requestData = "{\"tracking_number\": \"" + ydh + "\",\"carrier_code\":\"" + code + "\",\"lang\":\""+lang+"\"}";
            result = new Tracker().orderOnlineByJson(requestData, urlStr, "post");
        } catch (Exception e) {

        }
    }


    //public static void main(String[] args) {
    //    try {
    //        getWuliuInfo("YT1928321266059007");
    //
    //    } catch (Exception e) {
    //
    //    }
    //}


}

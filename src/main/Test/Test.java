import com.ymos.biz.WuLiuService;
import com.ymos.biz.WuLiuServiceImpl;
import com.ymos.common.Tracker;
import com.ymos.entity.Order;

import java.util.List;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

        //WuLiuService wuLiuService=new WuLiuServiceImpl();
        //List<Order> order= wuLiuService.getFindydh();
        //System.out.println(order);
        System.exit(0);

        try {
            // 1 List all carriers  查询所有的运输商简码
            // String urlStr = null;
            // String requestData="{\"tracking_number\":\"LA960805929CN\"}";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"carriers");
            // System.out.println("result======="+result);

            // 2 detect a carriers by tracking number  根据运单号获取运输商简码
             String urlStr = null;
             String requestData="{\"tracking_number\":\"LW738118754CN	\"}";
             String result = new Tracker().orderOnlineByJson(requestData,urlStr,"carriers/detect");
             System.out.println("result======="+result);

            // 3 create a tracking number 创建运输单
            // String	urlStr =null;
            // String requestData= "{\"tracking_number\": \"LW738118754CN\t\",\"carrier_code\":\"yunexpress\"}";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"post");
            // System.out.println("result======="+result);

            // 4 List all trackings
            // String	urlStr ="?page=1&limit=100&created_at_min=1521314361&created_at_max=1541314361&update_time_min=1521314361&update_time_max=1541314361&order_created_time_min=1521314361&order_created_time_max=1541314361&numbers=1212121,UG586285221CN&orders=123&lang=cn";
            // String requestData=null;
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"get");
            // System.out.println("result======="+result);

            // 5 Get tracking results of a single tracking.  获取单个物流信息
            // String	urlStr ="/yunexpress/YT1928121266056469/ch";
            // String requestData=null;
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"codeNumberGet");
            // System.out.println("result======="+result);

            // 6 create muti tracking number
            // String	urlStr =null;
            // String requestData="[{\"tracking_number\": \"YT1928121266056469\",\"carrier_code\":\"yunexpress\",\"title\":\"chase chen\",\"customer_name\":\"chase\",\"customer_email\":\"abc@qq.com\",\"order_id\":\"#123444\",\"order_create_time\":\"2018-05-20 12:00\",\"destination_code\":\"IL\",\"tracking_ship_date\":\"1525314361\",\"tracking_postal_code\":\"13ES20\",\"lang\":\"en\",\"logistics_channel\":\"4PX page\"},{\"tracking_number\": \"EA152563242CN\",\"carrier_code\":\"china-ems\",\"title\":\"chase chen\",\"customer_name\":\"chase\",\"customer_email\":\"abc@qq.com\",\"order_id\":\"#123444\",\"order_create_time\":\"2018-05-20 12:00\",\"destination_code\":\"IL\",\"tracking_ship_date\":\"1521314361\",\"tracking_postal_code\":\"13ES20\",\"lang\":\"en\",\"logistics_channel\":\"4PX page1\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"batch");
            // System.out.println("result======="+result);

            // 7 Update Tracking item
            // String	urlStr ="/yunexpress/YT1928121266056469";
            // String requestData="{\"title\": \"testtitle\",\"customer_name\":\"javatest\",\"customer_email\":\"942632688@qq.com\",\"order_id\":\"#1234567\",\"logistics_channel\":\"chase chen java\",\"customer_phone\":\"+86 13873399982\",\"destination_code\":\"US\",\"status\":\"7\"}";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"codeNumberPut");
            // System.out.println("result======="+result);

            // 8 Delete a tracking item
            // String	urlStr ="/yunexpress/YT1928121266056469";
            // String requestData=null;
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"codeNumberDelete");
            // System.out.println("result======="+result);

            // 9 Get realtime tracking results of a single tracking
            // String	urlStr =null;
            // String requestData="{\"tracking_number\": \"YT1928121266056469\",\"carrier_code\":\"yunexpress\"}";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"realtime");
            // System.out.println("result======="+result);

            // 10 Modify courier code
            // String	urlStr =null;
            // String requestData="{\"tracking_number\": \"YT1928121266056585\",\"carrier_code\":\"dhl\",\"update_carrier_code\":\"china-ems\"}";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"update");
            // System.out.println("result======="+result);

            // 11 Get user info
            // String	urlStr =null;
            // String requestData="YT1928121266056585";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"getuserinfo");
            // System.out.println("result======="+result);

            // 12 Get status number
            // String	urlStr =null;
            // String requestData=null;
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"getstatusnumber");
            // System.out.println("result======="+result);

            // 13 Set number not update  获取多个订单的  运单号和运输商简码
            // String	urlStr =null;
            // String requestData= "[{\"tracking_number\":\"LK032051658CN\",\"carrier_code\":\"china-ems\"},{\"tracking_number\":\"EA166023092CN\",\"carrier_code\":\"china-ems\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"notupdate");
            // System.out.println("result======="+result);

            // 14 Get remote iterm results
            // String	urlStr =null;
            // String requestData= "[{\"country\":\"CN\",\"postcode\":\"400422\"},{\"country\":\"CN\",\"postcode\":\"412000\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"remote");
            // System.out.println("result======="+result);

            // 15 Get cost time iterm results
            // String	urlStr =null;
            // String requestData= "[{\"carrier_code\":\"dhl\",\"destination\":\"US\",\"original\":\"CN\"},{\"carrier_code\":\"dhl\",\"destination\":\"RU\",\"original\":\"CN\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"costtime");
            // System.out.println("result======="+result);

            // 16 Delete multiple tracking item
            // String	urlStr =null;
            // String requestData= "[{\"tracking_number\":\"LK032051658CN\",\"carrier_code\":\"china-ems\"},{\"tracking_number\":\"LK032051658CN\",\"carrier_code\":\"china-ems\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"delete");
            // System.out.println("result======="+result);

            // 17 Update multiple Tracking item
            // String	urlStr =null;
            // String requestData="[{\"tracking_number\":\"LK032051658CN\",\"carrier_code\":\"china-ems\",\"title\": \"testtitle\",\"customer_name\":\"javatest\",\"customer_email\":\"942632688@qq.com\",\"order_id\":\"#123\",\"logistics_channel\":\"chase chen\",\"destination_code\":\"US\",\"status\":\"7\"},{\"tracking_number\":\"RM111516216CN\",\"carrier_code\":\"china-post\",\"title\": \"testtitle\",\"customer_name\":\"javatest\",\"customer_email\":\"942632688@qq.com\",\"order_id\":\"#123\",\"logistics_channel\":\"chase chen\",\"destination_code\":\"US\",\"status\":\"7\"}]";
            // String result = new Tracker().orderOnlineByJson(requestData,urlStr,"updatemore");
            // System.out.println("result======="+result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

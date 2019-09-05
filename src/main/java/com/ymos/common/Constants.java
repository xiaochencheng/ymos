

package com.ymos.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;


public final class Constants {
	
	public class SendConfig {
		/**
		 * 3秒
		 */
		public static final int Timeout = 80000;
		/**
		 * 一次从数据库取在条数
		 */
		public static final int Count = 2000;
		public static final int DyCount = 200;
		public static final int ThreadCount = 20;
		public static final int MAX_THREAD_COUNT = 60;
		public static final int ThreadStopMilSec = 1000;
		
	
		
	}
	
	public final static int getTerminusOperator(String operator){
		int i=0;
		switch (operator) {
		case "XL":
			i=3;
			break;
		case "ISAT":
			i=2;
			break;
		case "Telkomsel":
			i=1;
			break;
		case "Three":
			i=4;
			break;
		case "Axis":
			i=5;
			break;
		case "Smartfren":
			i=6;
			break;
		default:
			break;
		}
		return i;
	}
	
	public final static class ActionType{
		public final static int SCAN = 5;
		public final static int COMMENT = 4;
		public final static int SHARE = 3;
		public final static int LOGIN = 2;
		public final static int REG = 1;
		public final static int WX_CHECK = 6;
	}
	
	
	public static String mobPlus="http://m.bolomobi.com/c/p/1320a7664257465dbaabac0974a2fa51";
	public static String mobPlus_cpa="http://m.bolomobi.com/c/p/713250612a2a46999545345506131d1a";
	
    /*public static ObjectMapper json(){
    	ObjectMapper json=new ObjectMapper();
    	return json;
    }*/
    
    public static String getWeekDay(){
		Date today = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        String w=c.get(Calendar.DAY_OF_WEEK)+"";
        return w;
    }
    
    //SMS最多下发的条数
	public static int MAX_SEND_SMS=3;
	
	

	public static String ERROR_MSG = "ERROR_MSG";

	// user 在session中的key
	public static String USER_SESSION_KEY = "USER_SESSION_KEY";
	

	// CODE
	public static String CODE_CONFIG_DYNAMIC = "DYNAMIC";

	// PAGE SIZE
	public static int PAGE_SIZE = 15;

	public final static String REX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	public final static Pattern PTN_MOBILE = Pattern.compile("^1[358]\\d{9}$");
	
	public static String INSERT = ".insert";
	
	public static String UPDATE = ".update";
	
	public static String DELETE = ".delete";
	
	public static String QUERYBYID = ".queryById";
	
	public static String QUERY = ".query";
	
	public static String RealPath;
	
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    
    public static SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat yyyyMMdd1 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat yyyyMMddhhmmssSSS = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static String NOPERMISSIONS = "您没有权限修改!";
    
    public static String CODEEXIST = "该编号已存在,请换一个编号!";
    
    public static String MSG_SUCCESS = "保存成功!";
    
    public static String MSG_FAILURE = "保存失败!";
    
    public static final String VIEW_SUCCESS = "success";
	
	public static final String VIEW_FAILURE = "failure";
	
	public static final String VIEW_PROMPT = "prompt";
    
    /**
     * 请登录先
     */
    public static int STATUS_NOT_LOGIN = -1;
    
    public final static String TOKEN = "ZH-YY-TOKEN";
    
    public final static String CAPTCHA = "captcha";
    
    
    public final static String CACHE_APPS = "CACHE_APPS";
    
    public class ExtInfoVarName{
		
		public static final String  PHONE        = "phone";
		public static final String  PROVINCE     = "province";
		public static final String  CARRIER      = "carrier";
		public static final String  COUNTRY      = "country";
		public static final String  SERVICECOID  = "servicecoid";
		public static final String  CHANNELID    = "channelid";
		public static final String   LINKID      = "linkid";
		public static final String   MoOrStatus  = "MoOrStatus";
		public static final String   IMSI        = "IMSI";
		public static final String   ERROR_CAUSE = "ERROR_CAUSE";
		public static final String   PACKETID    = "packetid";
		public static final String   FEECODE     = "FeeCode";
		public static final String   BILLING_FLAG = "BillingFlag";
		public static final String   NEED_BILL = "NeedBill";
		public static final String   CHANNEL_TYPE = "ChannelType";
				
	}
    public class Area{
		public static final int UNKNOWN_COUNTRYID=101000000;
		public static final int UNKNOWN_PROVINCEID=101001000;

	}
    
    public class BigType{
    	public static final String MO="MO";
    	public static final String DR="DR";
    	public static final String MT="MT";
    	
    }
  //统计计费模式
  	public class StatisticFeeModeType{
  		public static final int REG = 1; //订阅
  		public static final int UNREG = 2; //退订
  		public static final int DR = 3; //DR计费
  	}

  	public class PaySP{
  		public static final int BLUE_PAY = 30031;
  		public static final int FORTUMO = 30024;
  		public static final int INFOBIP = 30019;
  		public static final int TIMWE = 30004;
  		public static final int IWALKER = 30033;
  		public static final int IONNEX = 30034;
  		public static final int MK = 80013;
  		public static final int EVERWORKS = 30037;
  		public static final int MK_MACAU = 30038;
  		public static final int AIPUWEISA = 30023;
  		public static final int UPAY = 30035;
  		public static final int IFREE = 30010;
  		public static final int MCOM = 30007;
  		public static final int TERMINUS = 30039;
  		public static final int TRIYAKOM=40001;
  		public static final int Funtastik=50001;
  		public static final int YONDU = 60001;
  		public static final int HIPHONE = 80010;
  		public static final int PALESTINE =10011;
  		public static final int GMOBI =10012;
  		public static final int LINKITID =90002;
  		public static final int CMPSMS =10013;
  		public static final int BLUEPLANET =10014;
  		public static final int VMG =10015;
  		public static final int NTH =10016;
  		public static final int FUNSEA=80012;
  		public static final int BELLCOMM=10018;
  		public static final int UNISTART=20010;
  		public static final int MOBILIELIFE=10021;
  		public static final int MSGCLOUD=10022;
		public static final int BELIEVE_MOBILE=80006;
		public static final int ALLTECO=10023;
		public static final int ZED=10024;
  	}

  	public class PayType{
  		public static final String REG = "reg";
  		public static final String UNREG = "unreg";
  		public static final String SUBSCRIBE_SUCCESS = "pay_success";
  		public static final String SUBSCRIBE_FAIL="pay_fail";
  	}
  	
  	public class StatusType{
  		public static final int SUCCESS = 1;
  		public static final int FAIL = 0;
  	}
  	
  	public class isSendToChannel{
  		public static final int SEND = 1;
  		public static final int  NOSEND= 0;
  	}
  	
  	
  	public class offerType{
  		public static final int  CPS= 0;
		public static final int CPA = 1;
  		public static final int DEMAND=2;
  	}

	public class ConfigurationType{
		public static final int  CPS= 0;
		public static final int CPA = 1;
		public static final int DEMAND=2;
	}

  	public class MoType{
  		public static final String REG="REG";
  		public static final String UNREG="UNREG";
  	}
  	public class DnType{
  		public static final String SUCCESS="success";
  		public static final String FAILED="failed";
  	}
  	
    
    public static void main(String[] args) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    	cal.setTimeInMillis(1414771250000l);
    	System.out.println(cal);
	}
    
    public static String DEFAULT_FROM_ID="8888";//默认住渠道
    public final static  String childFrom="000";//默认子渠道
    
    public class KEYWORDCODE{
  		public static final String RANDCONT = "{randCont}";
  	}
    
    public class Type{
  		public static final int REG = 1;
  		public static final int UNREG = 2;
  		public static final int SUBSCRIBE_SUCCESS = 3;
  		public static final int SUBSCRIBE_FAIL= 4;
  	}

    public class BtcType{
    	public static final String GATE="gate.io";
    	public static final String BITHUMB="bithumb";
    	public static final String HUOBI="huobi";
    }
	public class ChannelOfferConfigurationStatus{
		public static final String NORMAL="0";
		public static final String UPDATE="1";

	}
    public static String []BTCKEY={"BTC","ETH","DASH","LTC","ETC","XRP","BCH","ZEC","QTUM"};
    public static String BTCPRICE="usdt";
}
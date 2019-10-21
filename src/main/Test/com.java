import com.ymos.common.TransApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class com {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20191017000342245";
    private static final String SECURITY_KEY = "9EPuxYstl9_fLIdvcI0T";

    public static String UnicodeToCN(String unicodeStr) {
                 Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
                 Matcher matcher = pattern.matcher(unicodeStr);
                char ch;
                 while (matcher.find()) {
                        //group
                         String group = matcher.group(2);
                         //ch:'李四'
                        ch = (char) Integer.parseInt(group, 16);
                       //group1
                        String group1 = matcher.group(1);
                         unicodeStr = unicodeStr.replace(group1, ch + "");
                     }

                 return unicodeStr.replace("\\", "").trim();
            }

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String query="message";
        String ss=api.getTransResult(query,"en","zh");
      String ou=  UnicodeToCN(ss);
        System.out.println(ou);
    }





}

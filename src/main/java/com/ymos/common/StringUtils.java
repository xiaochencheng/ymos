package com.ymos.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class StringUtils {
    public final static ObjectMapper mapper = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false).configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    public static <T> T parseJsonToObj(InputStream json, Class<T> clazz){
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
    public static <T> T parseJsonToObj(String json, Class<T> clazz){
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String parseObjToJson(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
   public  static String enBase64(String msg){
        return Base64.getEncoder().encodeToString(msg.getBytes());
   }
    public  static String deBase64(String msg){
        return new String(Base64.getDecoder().decode(msg));
    }
    public static String encryptSHA256(String str) {
        MessageDigest md;
        String encode="";
        try {
            md=MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes("UTF-8"));
            encode=byteToHex(md.digest());
        } catch (Exception e) {
        }
        return encode;
    }
    public static String encryptSHAMAC(String message, String privateKey){
        try
        {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] digest = sha256_HMAC.doFinal(message.getBytes());
            String result = new BigInteger(1, digest).toString(16);
            return result;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    public static String byteToHex(byte[] bytes) {
        StringBuffer sb=new StringBuffer();
        String temp=null;
        for (int i = 0; i < bytes.length; i++) {
            temp=Integer.toHexString(bytes[i]&0XFF);
            if (temp.length()==1) {
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }
    public static String[] splitStr(String ids,String reg) {
        if(ids == null||reg==null) {
            return null;
        }
       return ids.split(reg);
    }

    public static Object defVal(Object obj, Object defVal) {
        return obj == null?defVal:(obj instanceof String && ((String)obj).isEmpty()?defVal:obj);
    }
    public static String md5Encode(String text, String salt) {
        return md5Encode(text, salt.hashCode());
    }

    public static String md5Encode(String text, int salt) {
        String hexString = new String(DigestUtils.md5DigestAsHex(text.getBytes()));
        String first = hexString.substring(0, 15);
        String second = hexString.substring(17);
        Long firstLong = Long.valueOf(Long.valueOf(first, 16).longValue() + (long)salt);
        Long secondLong = Long.valueOf(Long.valueOf(second, 16).longValue() + (long)salt);
        first = Long.toString(firstLong.longValue(), 16);
        second = Long.toString(secondLong.longValue(), 16);
        hexString = first + hexString.substring(15, 17) + second;
        return new String(DigestUtils.md5DigestAsHex(hexString.getBytes()));
    }

    public static String md5Encode(String text) {
        return new String(DigestUtils.md5DigestAsHex(text.getBytes()));
    }

    public static void lRedisSet(String key,String value) {
        if (key==null||"".equals(key)) {
            return;
        }
        Jedis jedis=null;
        try {
            jedis= RedisUtil.getJedis();
            jedis.lpush(key, value);
        } catch (Exception e) {
        }finally {
             RedisUtil.returnResource(jedis);
        }
    }

    public static void main(String[] args) {
        System.out.println(md5Encode("ulas123456","toufang"));
	}

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return !hasText(str);
    }

    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    public static String redisGet(String key){
        if (key==null||"".equals(key)) {
            return null;
        }
        Jedis jedis=null;
        try {
            jedis=RedisUtil.getJedis();
            return jedis.get(key);
        } catch (Exception e) {
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }

    public  static long getTimeMin(){
        Date a = new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return (calendar.getTime().getTime() - a.getTime())/1000;
    }

    public static void redisSetExpried(String key,String value,long time){
        if (key==null||"".equals(key)) {
            return;
        }
        Jedis jedis=null;
        try {
            jedis=RedisUtil.getJedis();
            Pipeline p = jedis.pipelined();
            p.set(key, value);
            p.expire(key,(int) time);
            p.sync();
        } catch (Exception e) {
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    public static void redisSet(String key,String value){
        if (key==null||"".equals(key)) {
            return;
        }
        Jedis jedis=null;
        try {
            jedis=RedisUtil.getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    public static String Md5(String sourceStr){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result= buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
        }
        return result;
    }
    public static String lRedisGet(String key) {
        if (key==null||"".equals(key)) {
            return null;
        }
        Jedis jedis=null;
        try {
            jedis=RedisUtil.getJedis();
            return jedis.lpop(key);
        } catch (Exception e) {
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }
    public static Date getDateAddDay(int add) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, add);
        return calendar.getTime();
    }
    public static int pareseInt(String string){
        int value=0;
        try {
            value=Integer.parseInt(string);
        } catch (Exception e) {
            return 0;
        }
        return value;
    }
    public static float pareseFloat(String string){
        float value=0f;
        try {
            value=Float.parseFloat(string);
        } catch (Exception e) {
            return 0f;
        }
        return value;
    }
    public  static String getUUID(){
        return UUID.randomUUID().toString();
    }
}

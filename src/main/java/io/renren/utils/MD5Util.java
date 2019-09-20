package io.renren.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util {

	final static String key = "hqjy_wechat_1658749461";


    public static String getKey() {
        return key;
    }

    /**
	 * MD5方法
	 * 
	 * @param text 明文
	 * @param key  密钥
	 * @param date 当前时间
	 * @return 密文
	 * @throws Exception
	 */
	public static String encrypt(String text, String key) {
		String encodeStr = null;
		//动态秘钥，根据时间变化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateKey = sdf.format(new Date());
		// 加密后的字符串
		try {
			String total = URLDecoder.decode(text, "utf-8") + key + dateKey;

			encodeStr = DigestUtils.md5Hex(total);
			
			System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodeStr;
	}

	/**
	 * MD5验证方法
	 * 
	 * @param text 明文
	 * @param key  密钥
	 * @param date 当前时间
	 * @param md5  密文
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String key, String md5) {
		// 根据传入的密钥进行验证
		String md5Text = encrypt(text, key);
		if (md5Text.equalsIgnoreCase(md5)) {
			return true;
		}
		return false;
	}
	 
	
	public static boolean verifyHasSign(String text) {
		String[] result = text.split("\\&sign=");
		if(result.length != 2) {
			return false;
		}
		// 根据传入的密钥进行验证
		return verify(result[0], key,result[1]);
	}
	
	/**
	 * 验签
	 * @param request
	 * @return
	 */
	/*public static boolean verifyAuto(HttpServletRequest request) {
		if(request == null || request.getQueryString() == null) {
			throw new FriendlyException("request is null");
		}
		return verifyHasSign(request.getQueryString());
	}*/

	public static void main(String[] args) {
		String params = "appid=wx04fe15f9b3b2c504";
		String sign = encrypt(params, key);
		System.out.println(verifyHasSign(params + "&sign=" + sign));
	
	}
}

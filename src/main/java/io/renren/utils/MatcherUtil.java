package io.renren.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则表达式校验工具
 *
 * @author ouchujian
 */
public class MatcherUtil {

    /**
     * 正整数
     */
    public final static String POSITIVE_INTEGER = "^[+]{0,1}(\\d+)$";

    /**
     * 正则表达式匹配方法
     *
     * @param value 待校验值
     * @param regEx 正则表达式
     * @return true 匹配,false不匹配
     */
    public static boolean matcher(String value, String regEx) {
    	if(StringUtils.isBlank(value))
    		return false;
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    public static void main(String[] args) {
    	System.out.println(matcher("-1111",POSITIVE_INTEGER));;
	}
}

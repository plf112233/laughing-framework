package com.laughing.help.tools.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
* @ClassName: StringUtil 
* @Description:字符串转换工具类
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年5月24日 下午3:15:22 
*
 */
public class StringUtil {
	
	public static List<Long> convertStringToLongList(String strText, String separator) {  
	    List<Long> list = new ArrayList<Long>();
        if (StringUtils.isBlank(strText)) {  
            return list; 
        }  
        String[] text = strText.split("\\" + separator); // 转换为数组  
        for (String str : text) {
            list.add(Long.valueOf(str));
        }
        return list;  
    }
	
	public static String convertStrToInStr(String datas, String split) {
		StringBuffer sb = new StringBuffer();
		try {
			String[] dataArr = datas.split(split);
			for (int i = 0; i < dataArr.length; i++) {
				sb.append("'");
				sb.append(dataArr[i]);
				sb.append("'");
				if (i != dataArr.length - 1) {
					sb.append(",");
				}
			}
		} catch (Exception e) {
		}
		return sb.toString();
	}
	public static String getSize(String size){
		DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(NumberUtils.toFloat(size)/1024/1024);
		return p;
	}
}

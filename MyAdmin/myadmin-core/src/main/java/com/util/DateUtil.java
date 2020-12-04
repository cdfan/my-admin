package com.util;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * 功能描述: Date 日期操作类
 * 
 * @author cdfan
 * @date 2020/5/22 15:59
 */
public class DateUtil {

    /**
     * 功能描述: 根据时间戳返回中文格式时间描述
     * 
     * @param second 时间戳单位秒
     * @return java.lang.String
     * @author cdfan
     * @date 2020/5/22 16:00
     */
    public static String secondToTime(long second) {
        // 转换天数
        long days = second / 86400;
        // 剩余秒数
        second = second % 86400;
        // 转换小时数
        long hours = second / 3600;
        // 剩余秒数
        second = second % 3600;
        // 转换分钟
        long minutes = second / 60;
        // 剩余秒数
        second = second % 60;
        if (0 < days) {
            return days + "天 " + hours + "小时 " + minutes + "分 " + second + "秒";
        } else {
            return hours + "小时 " + minutes + "分 " + second + "秒";
        }
    }

    /**
     * 功能描述: 将时间戳转换成时间格式
     * 
     * @param millis 时间戳单位毫秒
     * @return java.lang.String
     * @author cdfan
     * @date 2020/6/17 17:06
     */
    public static String millisToTimeStr(long millis) {
        StrBuilder strBuilder = new StrBuilder();
        long h = 60 * 60 * 1000;
        long m = 60 * 1000;
        long s = 1000;
        if (millis / h > 0) {
            strBuilder.append(millis / h).append("h:");
            millis = millis % h;
        }
        if (millis / m > 0) {
            strBuilder.append(millis / m).append("m:");
            millis = millis % m;
        }
        if (millis / s > 0) {
            strBuilder.append(millis / s).append("s:");
        }
        if (millis % s > 0) {
            strBuilder.append(millis % s).append("ms");
        }
        return strBuilder.toString();
    }
}

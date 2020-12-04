package com.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/3/10
 * @description: LocalDateTime 操作类
 */
public class LocalDateTimeUtil {

    // 获取当前时间的LocalDateTime对象
    // LocalDateTime.now();

    // 根据年月日构建LocalDateTime
    // LocalDateTime.of();

    // 比较日期先后
    // LocalDateTime.now().isBefore(),
    // LocalDateTime.now().isAfter(),

    /**
     * 功能描述: Date转换为LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 功能描述: LocalDateTime转换为Date
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 功能描述:获取指定日期的毫秒
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 功能描述: 获取指定日期的秒
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 功能描述: 获取指定时间的指定格式
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 功能描述:获取当前时间的指定格式
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 功能描述: 根据指定格式获取时间，默认格式为yyyy-MM-dd HH:mm:ss, 注意转换为LocalDateTime必须包含时分秒，如果需要转换为yyyy-MM-dd，请使用parseDateTimeByDate
     */
    public static LocalDateTime parseDateTime(String time, String pattern) {
        pattern = StringUtils.isEmpty(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern;
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 功能描述: 根据日期格式（yyyy-MM-dd）获取时间
     */
    public static LocalDateTime parseDateTimeByDate(String date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[['T'HH][:mm][:ss]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .toFormatter();
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * 功能描述: 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 功能描述: 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     */
    public static LocalDateTime minus(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个日期的差 field参数为ChronoUnit.*
     * 
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @param field 单位(年月日时分秒)
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 功能描述: 获取一天的开始时间，2017,7,22 00:00
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 功能描述:获取一天的结束时间，2017,7,22 23:59:59.999999999
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

}

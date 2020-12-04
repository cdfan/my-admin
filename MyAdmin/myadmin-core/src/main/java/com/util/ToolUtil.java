package com.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exception.MyAdminException;

/**
 * 功能描述: 高频方法集合类
 * 
 * @author cdfan
 * @date 2020/5/25 16:53
 */
public class ToolUtil {

    /**
     * 获取异常的具体信息
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
            return sw.getBuffer().toString();
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
     *
     * @param obj 被计算长度的对象
     * @return 长度
     */
    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>)obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>)obj).size();
        }

        int count;
        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>)obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>)obj;
            count = 0;
            while (enumeration.hasMoreElements()) {
                count++;
                enumeration.nextElement();
            }
            return count;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        return -1;
    }


    /**
     * 强转->string,并去掉多余空格
     */
    public static String toStr(Object str) {
        return toStr(str, "");
    }

    /**
     * 强转->string,并去掉多余空格
     */
    public static String toStr(Object str, String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return str.toString().trim();
    }


    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String fileWholeName) {
        if (ObjectUtils.isEmpty(fileWholeName)) {
            return "";
        }
        int lastIndexOf = fileWholeName.lastIndexOf(".");
        return fileWholeName.substring(lastIndexOf);
    }

    /**
     * 功能描述: 将list转为树结构。
     * 
     * @param listData 需要转换的数据，要求list中为map
     * @param id 每个map中包含id（当前节点id的key）
     * @param pid pid（当前节点的父id的key）
     * @param disabledPNode 是否禁用父节点，如果为true生成的树结构中父节点将会添加:"disabled: true"
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/5/22 16:09
     */
    public static List<Map<String, Object>> treeBuilder(List<Map<String, Object>> listData, String id, String pid, boolean disabledPNode) {
        // 最终结果
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 复制对象
        List<Map> list = new ArrayList<>(listData);
        Iterator it = list.iterator();
        Map item;
        while (it.hasNext()) {
            item = (Map)it.next();
            // 如果是父节点
            if (ObjectUtils.isEmpty(item.get(pid))) {
                if(disabledPNode){
                    item.put("disabled", true);
                }
                resultList.add(item);
                it.remove();
            }
        }
        // 需要删除的元素，因为在递归中删除元素会报错（iterate.remove只能在一层循环中删除）
        List<Map> removeList = new LinkedList<>();
        it = resultList.iterator();
        while (it.hasNext()) {
            item = (Map)it.next();
            findChildren(item, list, id, pid, removeList, disabledPNode);
            // 删除已添加的元素
            if (removeList.size() > 0) {
                list.removeAll(removeList);
                removeList.clear();
            }
        }

        return resultList;
    }

    private static Map findChildren(Map tree, List<Map> list, String id, String pid, List<Map> removeList, boolean disabledPNode) {
        for (Map node : list) {
            if (tree.get(id).toString().equals(node.get(pid).toString())) {
                tree.computeIfAbsent("children", k -> new ArrayList<Map>());
                if(disabledPNode){
                    tree.put("disabled", true);
                }
                ((List<Map>)tree.get("children")).add(findChildren(node, list, id, pid, removeList, disabledPNode));
                removeList.add(node);
            }
        }
        return tree;
    }

    /**
     * 功能描述: 添加日期查询范围
     * 
     * @param queryWrapper 查询对象
     * @param createTime 日期范围，格式必须为yyyy-MM-dd,yyyy-MM-dd
     * @param filed 需要判断范围的数据库字段名
     * @author cdfan
     * @date 2020/6/18 16:26
     */
    public static <T> QueryWrapper<T> addTimeQuery(QueryWrapper<T> queryWrapper, String createTime, String filed) {
        String[] times = createTime.split(",");
        if (ObjectUtils.isEmpty(times) || times.length != 2) {
            throw new MyAdminException("查询日期不符合法");
        }
        LocalDateTime dayStart = LocalDateTimeUtil.getDayStart(LocalDateTimeUtil.parseDateTimeByDate(times[0]));
        LocalDateTime dayEnd = LocalDateTimeUtil.getDayEnd(LocalDateTimeUtil.parseDateTimeByDate(times[1]));
        queryWrapper = queryWrapper.between(filed, dayStart, dayEnd);
        return queryWrapper;
    }
}
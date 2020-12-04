package com.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author xiaoleilu
 *
 */
public class StrUtil {

    private static final String EMPTY = "";
    private static final String UNDERLINE = "_";

    /**
     * 去掉指定前缀
     * 
     * @param str 字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(String str, String prefix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(prefix)) {
            return str;
        }

        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 忽略大小写去掉指定前缀
     * 
     * @param str 字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
     */
    public static String removePrefixIgnoreCase(String str, String prefix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(prefix)) {
            return str;
        }

        if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 去掉指定后缀
     * 
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 忽略大小写去掉指定后缀
     * 
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffixIgnoreCase(String str, String suffix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(suffix)) {
            return str;
        }

        if (str.toLowerCase().endsWith(suffix.toLowerCase())) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 如果给定字符串不是以prefix开头的，在开头补充 prefix
     * 
     * @param str 字符串
     * @param prefix 前缀
     * @return 补充后的字符串
     */
    public static String addPrefixIfNot(String str, String prefix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(prefix)) {
            return str;
        }
        if (!str.startsWith(prefix)) {
            str = prefix + str;
        }
        return str;
    }

    /**
     * 如果给定字符串不是以suffix结尾的，在尾部补充 suffix
     * 
     * @param str 字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     */
    public static String addSuffixIfNot(String str, String suffix) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(suffix)) {
            return str;
        }
        if (!str.endsWith(suffix)) {
            str += suffix;
        }
        return str;
    }

    /**
     * 清理空白字符
     * 
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s*", EMPTY);
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(UNDERLINE)) {
            name = name.toLowerCase();

            StringBuilder sb = new StringBuilder(name.length());
            boolean upperCase = false;
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);

                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * 
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 补充字符串以满足最小长度 StrUtil.padPre("1", 3, '0');//"001"
     *
     * @param str 字符串
     * @param minLength 最小长度
     * @param padChar 补充的字符
     * @return 补充后的字符串
     */
    public static String padPre(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 补充字符串以满足最小长度 StrUtil.padEnd("1", 3, '0');//"100"
     * 
     * @param str 字符串
     * @param minLength 最小长度
     * @param padChar 补充的字符
     * @return 补充后的字符串
     */
    public static String padEnd(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(str);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

}

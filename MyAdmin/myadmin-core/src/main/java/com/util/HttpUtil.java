package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 功能描述:获取http请求相关数据
 * 
 * @author cdfan
 * @date 2020/2/19 17:17
 */
public class HttpUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 功能描述: 获取请求的ip
     */
    public static String getIp() {
        HttpServletRequest request = HttpUtil.getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 功能描述: 获取本机ip，如果有多个用逗号+空格隔开
     * 
     * @return java.lang.String
     * @author cdfan
     * @date 2020/6/24 14:35
     */
    public static String getLocalIp() {
        StringBuilder ips = new StringBuilder();
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            if(ObjectUtils.isEmpty(ips)){
                                ips.append(ip.getHostAddress());
                            }else{
                                ips.append(", ");
                                ips.append(ip.getHostAddress());
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return ips.toString();
    }

    /**
     * 功能描述: 获取客户端浏览器类型
     * 
     * @return java.lang.String
     * @author cdfan
     * @date 2020/6/3 17:59
     */
    public static String getBrowser() {
        HttpServletRequest request = HttpUtil.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 功能描述: 获取客户端操作系统名称
     * 
     * @return java.lang.String
     * @author cdfan
     * @date 2020/6/3 17:59
     */
    public static String getOSName() {
        HttpServletRequest request = HttpUtil.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem system = userAgent.getOperatingSystem();
        return system.getName();
    }

    /**
     * 根据ip获取详细地址 结果默认格式: 国家|区域|省份|城市|ISP 方法返回格式：国家省份城市 ISP
     */
    public static String getAddressForIp(String ip) {
        DbSearcher searcher = null;
        try {
            DbConfig config = new DbConfig();
            Resource resource = new ClassPathResource("ip2region/ip2region.db");
            // 打成jar包后，如果直接读取文件路径，由于是压缩包路径中会出现！，从而报文件名、目录名或卷标语法不正确，
            // 这里替换为将数据文件加载到内存然后再内存中查询
            // String filePath =
            // Objects.requireNonNull(HttpUtil.class.getClassLoader().getResource("ip2region/ip2region.db")).getPath();
            // Method method = searcher.getClass().getMethod("btreeSearch", String.class);

            searcher = new DbSearcher(config, IOUtil.readInputStream(resource.getInputStream()));
            DataBlock dataBlock = searcher.memorySearch(ip);
            String address = dataBlock.getRegion();
            String[] addrItem = address.split("\\|");
            StringBuilder addr = new StringBuilder();
            for (int i = 0; i < addrItem.length; i++) {
                if (!"0".equals(addrItem[i])) {
                    if (i == addrItem.length - 1) {
                        if (!"内网IP".equals(addrItem[i])) {
                            addr.append(" ").append(addrItem[i]);
                        }
                    } else {
                        addr.append(addrItem[i]);
                    }
                }
            }
            return addr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (Exception ignored) {
                }
            }

        }
        return "";
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取 HttpServletRequest
     * 
     * @return request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            StringBuilder query = new StringBuilder();

            for (Map.Entry<String, String> kv : param.entrySet()) {
                query.append(URLEncoder.encode(kv.getKey(), "UTF-8")).append("=");
                query.append(URLEncoder.encode(kv.getValue(), "UTF-8")).append("&");
            }
            if (query.lastIndexOf("&") > 0) {
                query.deleteCharAt(query.length() - 1);
            }

            String urlNameString = url + "?" + query.toString();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            StringBuilder paraBuilder = new StringBuilder();
            for (String key : param.keySet()) {
                paraBuilder.append(key).append("=").append(param.get(key)).append("&");
            }
            String para = paraBuilder.toString();
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}

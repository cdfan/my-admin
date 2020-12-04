package com.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.exception.MyAdminException;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/12
 * @description: IO流操作工具类
 */
public class IOUtil {

    /**
     * 功能描述: 读取输入流转并返回字节
     * 
     * @param is InputStream
     * @return byte[]
     * @author cdfan
     * @date 2020/8/12 12:13
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int length;
            byte[] buffer = new byte[1024];
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyAdminException("流读取失败");
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(baos);
        }
        return baos.toByteArray();
    }

}

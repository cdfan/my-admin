package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.exception.MyAdminException;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/7
 * @description: zip压缩工具类，补充cn.hutool.core.util.ZipUtil中的方法
 */
public class ZipUtil {

    /**
     * 功能描述: 将提供的字节数据压缩成zip
     * 
     * @param dirName 压缩后数据存储的目录名称
     * @param zipData 压缩的数据其中map的key表示当前byte对应的文件名称，而byte则是需要压缩的数据
     * @return byte[] 压缩后压缩包的字节对象
     * @author cdfan
     * @date 2020/8/7 14:39
     */
    public static byte[] zipByte(String dirName, Map<String, byte[]> zipData) {
        ZipOutputStream zip = null;
        ByteArrayOutputStream outputStream = null;
        byte[] data;
        try {
            outputStream = new ByteArrayOutputStream();
            zip = new ZipOutputStream(outputStream);
            for (String fileName : zipData.keySet()) {
                zip.putNextEntry(new ZipEntry(dirName + File.separator + fileName));
                IOUtils.write(zipData.get(fileName), zip);
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyAdminException("压缩数据失败");
        }finally {
            IOUtils.closeQuietly(zip);
            IOUtils.closeQuietly(outputStream);
        }
        // 一定要在流关闭之后在获取数据，不然会出现不可预料的压缩文件末端的错误
        data =  outputStream.toByteArray();
        return data;
    }
}

package com.util;

import cn.hutool.core.io.IoUtil;
import com.exception.MyAdminException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 功能描述: 文件操作工具类
 * 
 * @author cdfan
 * @date 2020/5/22 16:00
 */
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 功能描述: 根据上传的数据生成文件
     * 
     * @param file 上传文件
     * @param rootPath 上传文件根目录，通常为 MyAdminProperties.resourcePath
     * @param dirPath 将文件生成在上传文件根目录的哪个文件夹下, 通常为 resources/xxx
     * @param fileName 文件名，不包括后缀，如果不传默认为当前时间戳
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author cdfan
     * @date 2020/5/22 16:27
     */
    public static Map<String, String> uploadFile(MultipartFile file, String rootPath, String dirPath, String fileName) {
        try {
            String fileType = ToolUtil.getFileSuffix(file.getOriginalFilename());
            if (StringUtils.isEmpty(fileName)) {
                fileName = new Date().getTime() + fileType;
            } else {
                fileName = fileName + fileType;
            }

            File upload = new File(rootPath + dirPath);
            if (!upload.exists()) {
                upload.mkdirs();
            }
            String filePath = makeFilePath(upload.getPath(), fileName);

            File serverFile = new File(filePath);
            file.transferTo(serverFile);
            Map<String, String> map = new HashMap<String, String>();
            // 状态
            map.put("state", "true");
            // 文件全路径
            map.put("fileAllPath", filePath);
            // 文件访问url，不包括ip和端口
            map.put("url", "/" + makeFilePath(dirPath, fileName));
            // 文件类型
            map.put("fileType", fileType);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<String, String>();
            map.put("state", "false");
            map.put("msg", "文件生成失败");
            return map;
        }
    }

    /**
     * 得到文件的输入流，如无法定位文件返回null。
     * 
     * @param relativePath 文件相对当前应用程序的类加载器的路径。
     * @return 文件的输入流。
     */
    public static InputStream getResourceStream(String relativePath) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePath);
    }

    /**
     * 关闭输入流。
     * 
     * @param is 输入流，可以是null。
     */
    public static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 关闭输出流。
     * 
     * @param is 输出流，可以是null。
     */
    public static void closeFileOutputStream(FileOutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 从文件路径中提取目录路径，如果文件路径不含目录返回null。
     *
     * @param filePath 文件路径。
     * @return 目录路径，不以'/'或操作系统的文件分隔符结尾。
     */
    public static String extractDirPath(String filePath) {
        // 分隔目录和文件名的位置
        int separatePos = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        return separatePos == -1 ? null : filePath.substring(0, separatePos);
    }

    /**
     * 从文件路径中提取文件名, 如果不含分隔符返回null
     *
     * @param filePath 文件路径。
     * @return 文件名, 如果不含分隔符返回null
     */
    public static String extractFileName(String filePath) {
        // 分隔目录和文件名的位置
        int separatePos = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        return separatePos == -1 ? null : filePath.substring(separatePos + 1, filePath.length());
    }

    /**
     * 按路径建立文件，如已有相同路径的文件则不建立。
     *
     * @param filePath 要建立文件的路径。
     * @return 表示此文件的File对象。
     */
    public static File makeFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            return file;
        }
        if (filePath.endsWith("/") || filePath.endsWith("\\")) {
            try {
                throw new IOException(filePath + " is a directory");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 文件所在目录的路径
        String dirPath = extractDirPath(filePath);
        // 如文件所在目录不存在则先建目录
        if (dirPath != null) {
            makeFolder(dirPath);
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 新建目录,支持建立多级目录
     *
     * @param folderPath 新建目录的路径字符串
     * @return boolean, 如果目录创建成功返回true, 否则返回false
     */
    public static boolean makeFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 要删除文件名及路径
     * @return boolean 删除成功返回true,删除失败返回false
     */
    public static boolean deleteFile(String filePathAndName) {
        try {
            File myDelFile = new File(filePathAndName);
            if (myDelFile.exists()) {
                myDelFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 递归删除指定目录中所有文件和子文件夹
     *
     * @param path 某一目录的路径,如"c:\cs"
     * @param ifDeleteFolder boolean值,如果传true,则删除目录下所有文件和文件夹;如果传false,则只删除目录下所有文件,子文件夹将保留
     */
    public static void deleteAllFile(String path, boolean ifDeleteFolder) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        String temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith("\\") || path.endsWith("/")) {
                temp = path + tempList[i];
            } else {
                temp = path + File.separator + tempList[i];
            }
            if ((new File(temp)).isFile()) {
                deleteFile(temp);
            } else if ((new File(temp)).isDirectory() && ifDeleteFolder) {
                // 先删除文件夹里面的文件
                deleteAllFile(path + File.separator + tempList[i], ifDeleteFolder);
                // 再删除空文件夹
                deleteFolder(path + File.separator + tempList[i]);
            }
        }
    }

    /**
     * 删除文件夹,包括里面的文件
     *
     * @param folderPath 文件夹路径字符串
     */
    public static void deleteFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (myFilePath.exists()) {
                // 删除完里面所有内容
                deleteAllFile(folderPath, true);
                // 删除空文件夹
                myFilePath.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件,如果目标文件的路径不存在,会自动新建路径
     *
     * @param sourcePath 源文件路径, e.g. "c:/cs.txt"
     * @param targetPath 目标文件路径 e.g. "f:/bb/cs.txt"
     */
    public static void copyFile(String sourcePath, String targetPath) {
        InputStream inStream = null;
        FileOutputStream fos = null;
        try {
            int byteSum = 0;
            int byteRead = 0;
            File sourcefile = new File(sourcePath);
            // 文件存在时
            if (sourcefile.exists()) {
                // 读入原文件
                inStream = new FileInputStream(sourcePath);
                // 文件所在目录的路径
                String dirPath = extractDirPath(targetPath);
                // 如文件所在目录不存在则先建目录
                if (dirPath != null) {
                    makeFolder(dirPath);
                }
                fos = new FileOutputStream(targetPath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead;
                    fos.write(buffer, 0, byteRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(inStream);
            closeFileOutputStream(fos);
        }
    }

    /**
     * 将路径和文件名拼接起来
     *
     * @param folderPath 某一文件夹路径字符串，e.g. "c:\cs\" 或 "c:\cs"
     * @param fileName 某一文件名字符串, e.g. "cs.txt"
     * @return 文件全路径的字符串
     */
    public static String makeFilePath(String folderPath, String fileName) {
        return folderPath.endsWith("\\") || folderPath.endsWith("/") ? folderPath + fileName
            : folderPath + File.separatorChar + fileName;
    }

    /**
     * 将某一文件夹下的所有文件和子文件夹拷贝到目标文件夹，若目标文件夹不存在将自动创建
     *
     * @param sourcePath 源文件夹字符串，e.g. "c:\cs"
     * @param targetPath 目标文件夹字符串，e.g. "d:\tt\qq"
     */
    public static void copyFolder(String sourcePath, String targetPath) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            // 如果文件夹不存在 则建立新文件夹
            makeFolder(targetPath);
            String[] file = new File(sourcePath).list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                String tempPath = makeFilePath(sourcePath, file[i]);
                temp = new File(tempPath);
                String target = "";
                if (temp.isFile()) {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(target = makeFilePath(targetPath, file[i]));
                    byte[] b = new byte[1024 * 5];
                    int len = 0;
                    int sum = 0;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                        sum += len;
                    }
                    target = target + "";
                    output.flush();
                    closeInputStream(input);
                    closeFileOutputStream(output);
                // 如果是子文件夹
                } else if (temp.isDirectory()) {
                    copyFolder(sourcePath + '/' + file[i], targetPath + '/' + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(input);
            closeFileOutputStream(output);
        }
    }

    /**
     * 移动文件
     *
     * @param oldFilePath 旧文件路径字符串, e.g. "c:\tt\cs.txt"
     * @param newFilePath 新文件路径字符串, e.g. "d:\kk\cs.txt"
     */
    public static void moveFile(String oldFilePath, String newFilePath) {
        copyFile(oldFilePath, newFilePath);
        deleteFile(oldFilePath);
    }

    /**
     * 移动文件夹
     *
     * @param oldFolderPath 旧文件夹路径字符串，e.g. "c:\cs"
     * @param newFolderPath 新文件夹路径字符串，e.g. "d:\cs"
     */
    public static void moveFolder(String oldFolderPath, String newFolderPath) {
        copyFolder(oldFolderPath, newFolderPath);
        deleteFolder(oldFolderPath);

    }

    /**
     * 获得某一文件夹下的所有文件的路径集合
     *
     * @param filePath 文件夹路径
     * @return ArrayList，其中的每个元素是一个文件的路径的字符串
     */
    public static ArrayList<String> getFilePathFromFolder(String filePath) {
        ArrayList<String> fileNames = new ArrayList<String>();
        File file = new File(filePath);
        try {
            File[] tempFile = file.listFiles();
            for (int i = 0; i < tempFile.length; i++) {
                if (tempFile[i].isFile()) {
                    String tempFileName = tempFile[i].getName();
                    fileNames.add(makeFilePath(filePath, tempFileName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    /**
     * 递归遍历文件目录,获取所有文件路径
     *
     * @param filePath
     * @return 2012-1-4
     */
    public static ArrayList<String> getAllFilePathFromFolder(String filePath) {
        ArrayList<String> filePaths = new ArrayList<String>();
        File file = new File(filePath);
        try {
            File[] tempFile = file.listFiles();
            for (int i = 0; i < tempFile.length; i++) {
                String tempFileName = tempFile[i].getName();
                String path = makeFilePath(filePath, tempFileName);
                if (tempFile[i].isFile()) {
                    filePaths.add(path);
                } else {
                    ArrayList<String> tempFilePaths = getAllFilePathFromFolder(path);
                    if (tempFilePaths.size() > 0) {
                        for (String tempPath : tempFilePaths) {
                            filePaths.add(tempPath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePaths;
    }

    /**
     * 获得某一文件夹下的所有文件的总数
     *
     * @param filePath 文件夹路径
     * @return int 文件总数
     */
    public static int getFileCount(String filePath) {
        int count = 0;
        try {
            File file = new File(filePath);
            if (!isFolderExist(filePath)) {
                return count;
            }
            File[] tempFile = file.listFiles();
            for (int i = 0; i < tempFile.length; i++) {
                if (tempFile[i].isFile()) {
                    count++;
                }
            }
        } catch (Exception fe) {
            count = 0;
        }
        return count;
    }

    /**
     * 获得某一路径下要求匹配的文件的个数
     *
     * @param filePath 文件夹路径
     * @param matchs 需要匹配的文件名字符串,如".*a.*",如果传空字符串则不做匹配工作 直接返回路径下的文件个数
     * @return int 匹配文件名的文件总数
     */
    public static int getFileCount(String filePath, String matchs) {
        int count = 0;
        if (!isFolderExist(filePath)) {
            return count;
        }
        if (matchs.equals("") || matchs == null) {
            return getFileCount(filePath);
        }
        File file = new File(filePath);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].isFile()) {
                if (Pattern.matches(matchs, tempFile[i].getName())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 获得某一文件的行数
     *
     * @param filePath 文件夹路径
     * @return int 行数
     */
    public static int getFileLineCount(String filePath) {
        if (!isFileExist(filePath))
            return 0;
        FileReader fr = null;
        BufferedReader br = null;
        int count = 0;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            while ((br.readLine()) != null) {
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 判断某一文件是否为空
     *
     * @param filePath 文件的路径字符串，e.g. "c:\cs.txt"
     * @return 如果文件为空返回true, 否则返回false
     * @throws IOException
     */
    public static boolean ifFileIsNull(String filePath) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader(filePath);
        if (fr.read() == -1) {
            result = true;
        }
        fr.close();
        return result;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件路径字符串，e.g. "c:\cs.txt"
     * @return 若文件存在返回true, 否则返回false
     */
    public static boolean isFileExist(String fileName) {
        // 判断文件名是否为空
        if (fileName == null || fileName.length() == 0) {
            return false;
        } else {
            // 读入文件 判断文件是否存在
            File file = new File(fileName);
            if (!file.exists() || file.isDirectory()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param folderPath 文件夹路径字符串，e.g. "c:\cs"
     * @return 若文件夹存在返回true, 否则返回false
     */
    public static boolean isFolderExist(String folderPath) {
        File file = new File(folderPath);
        return file.isDirectory() ? true : false;
    }

    /**
     * 获得文件的大小
     *
     * @param filePath 文件路径字符串，e.g. "c:\cs.txt"
     * @return 返回文件的大小, 单位kb, 如果文件不存在返回null
     */
    public static Double getFileSize(String filePath) {
        if (!isFileExist(filePath))
            return null;
        else {
            File file = new File(filePath);
            double intNum = Math.ceil(file.length() / 1024.0);
            return new Double(intNum);
        }

    }

    /**
     * 获得文件的大小,字节表示
     *
     * @param filePath 文件路径字符串，e.g. "c:\cs.txt"
     * @return 返回文件的大小, 单位kb, 如果文件不存在返回null
     */
    public static Double getFileByteSize(String filePath) {
        if (!isFileExist(filePath))
            return null;
        else {
            File file = new File(filePath);
            double intNum = Math.ceil(file.length());
            return new Double(intNum);
        }

    }

    /**
     * 获得文件的最后修改时间
     *
     * @param filePath 文件路径字符串，e.g. "c:\cs.txt"
     * @return 返回文件最后的修改日期的字符串, 如果文件不存在返回null
     */
    public static String fileModifyTime(String filePath) {
        if (!isFileExist(filePath)) {
            return null;
        } else {
            File file = new File(filePath);

            long timeStamp = file.lastModified();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String tsForm = formatter.format(new Date(timeStamp));
            return tsForm;
        }
    }

    /**
     * 遍历某一文件夹下的所有文件,返回一个ArrayList,每个元素又是一个子ArrayList, 子ArrayList包含三个字段,依次是文件的全路径(String),文件的修改日期(String), 文件的大小(Double)
     *
     * @param folderPath 某一文件夹的路径
     * @return ArrayList
     */
    public static ArrayList getFilesSizeModifyTime(String folderPath) {
        List returnList = new ArrayList();
        List filePathList = getFilePathFromFolder(folderPath);
        for (int i = 0; i < filePathList.size(); i++) {
            List tempList = new ArrayList();
            String filePath = (String)filePathList.get(i);
            String modifyTime = FileUtil.fileModifyTime(filePath);
            Double fileSize = FileUtil.getFileSize(filePath);
            tempList.add(filePath);
            tempList.add(modifyTime);
            tempList.add(fileSize);
            returnList.add(tempList);
        }
        return (ArrayList)returnList;
    }

    /**
     * 获得某一文件夹下的所有TXT，txt文件名的集合
     *
     * @param filePath 文件夹路径
     * @return ArrayList，其中的每个元素是一个文件名的字符串
     */
    public static ArrayList getTxtFileNameFromFolder(String filePath) {
        ArrayList fileNames = new ArrayList();
        File file = new File(filePath);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].isFile()) {
                if (tempFile[i].getName().indexOf("TXT") != -1 || tempFile[i].getName().indexOf("txt") != -1) {
                    fileNames.add(tempFile[i].getName());
                }
            }
        }
        return fileNames;
    }

    /**
     * 获得某一文件夹下的所有xml，XML文件名的集合
     *
     * @param filePath 文件夹路径
     * @return ArrayList，其中的每个元素是一个文件名的字符串
     */
    public static ArrayList getXmlFileNameFromFolder(String filePath) {
        ArrayList fileNames = new ArrayList();
        File file = new File(filePath);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].isFile()) {
                if (tempFile[i].getName().indexOf("XML") != -1 || tempFile[i].getName().indexOf("xml") != -1) {
                    fileNames.add(tempFile[i].getName());
                }
            }
        }
        return fileNames;
    }

    /**
     * 校验文件是否存在
     *
     * @param fileName String 文件名称
     * @param mapErrorMessage Map 错误信息Map集
     * @return boolean 校验值
     */
    public static boolean checkFile(String fileName, HashMap mapErrorMessage) {
        if (mapErrorMessage == null) {
            mapErrorMessage = new HashMap();
        }
        // 判断文件名是否为空
        if (fileName == null) {
            fileName = "";
        }
        // 判断文件名长度是否为0
        if (fileName.length() == 0) {
            mapErrorMessage.put("errorMessage", "fileName length is 0");
            return false;
        } else {
            // 读入文件 判断文件是否存在
            File file = new File(fileName);
            if (!file.exists() || file.isDirectory()) {
                mapErrorMessage.put("errorMessage", fileName + "is not exist!");
                return false;
            }
        }
        return true;
    }

    /**
     * 校验文件是否存在
     *
     * @param fileName String 文件名称
     * @return boolean 校验值
     */
    public static boolean checkFile(String fileName) {
        // 判断文件名是否为空
        if (fileName == null) {
            fileName = "";
        }
        // 判断文件名长度是否为0
        if (fileName.length() == 0) {
            return false;
        } else {
            // 读入文件 判断文件是否存在
            File file = new File(fileName);
            if (!file.exists() || file.isDirectory()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 新建目录
     *
     * @param folderPath String 如 c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 文件重命名
     *
     * @param path 文件路径
     * @param oldname 原有的文件名
     * @param newname 新的文件名
     */
    public static Map<String, Object> renameFile(String path, String oldname, String newname) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 新的文件名和以前文件名不同时,才有必要进行重命名
        if (!oldname.equals(newname)) {
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (!oldfile.exists()) {
                log.error("需要重命名的文件不存在");
                map.put("code", false);
                map.put("message", "需要重命名的文件不存在");
                return map;
            }
            // 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            if (newfile.exists()) {
                log.error(newname + "已经存在");
                map.put("code", false);
                map.put("message", newname + "文件已经存在");
                return map;
            } else {
                boolean isSuccess = oldfile.renameTo(newfile);
                map.put("code", isSuccess);
                map.put("message", "重命名成功");
                return map;
            }
        } else {
            map.put("code", false);
            map.put("message", "新文件名和旧文件名相同");
            log.error("新文件名和旧文件名相同...");
            return map;
        }
    }

    /**
     * 通过字节创建文件
     * 
     * @param: @param 字节流
     * @param: @param 指定目录
     * @param: @param 文件名称
     * @return: void
     */
    public static void outbyte(byte[] byteData, String path, String fileName) {
        FileOutputStream out;

        try {
            File outFile = new File(path);

            if (!outFile.exists()) {
                outFile.mkdirs();
            }

            out = new FileOutputStream(outFile + File.separator + fileName);
            out.write(byteData);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地图片转换成base64字符串
     *
     * @param imgFile 图片本地路径
     */
    public static String ImageToBase64ByLocal(String imgFile) {

        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 功能描述: 下载文件
     * 
     * @param response HttpServletResponse
     * @param file 要下载的文件
     * @author cdfan
     * @date 2020/7/8 10:31
     */
    public static void downloadFile(HttpServletResponse response, File file) {
        response.setContentType("application/octet-stream; charset=UTF-8");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IoUtil.copy(fis, response.getOutputStream(), IoUtil.DEFAULT_BUFFER_SIZE);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyAdminException("下载文件失败");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyAdminException("下载文件失败");
                }
            }
        }
    }

    /**
     * 功能描述: 下载文件
     *
     * @param response HttpServletResponse
     * @param bytes 要下载的数据
     * @param fileName 下载数据的文件名
     * @author cdfan
     * @date 2020/7/8 10:31
     */
    public static void downloadFile(HttpServletResponse response, byte[] bytes, String fileName) {
        response.setContentType("application/octet-stream; charset=UTF-8");
        ByteArrayInputStream fis = null;
        try {
            fis = new ByteArrayInputStream(bytes);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IoUtil.copy(fis, response.getOutputStream(), IoUtil.DEFAULT_BUFFER_SIZE);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyAdminException("下载文件失败");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyAdminException("下载文件失败");
                }
            }
        }
    }
}
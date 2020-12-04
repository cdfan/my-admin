package com.myadminmain.tools.editor.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.resultdata.ResultData;
import com.exception.MyAdminException;
import com.myadminmain.config.properties.MyAdminProperties;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.core.shiro.ShiroUtil;
import com.util.FileUtil;
import com.util.LocalDateTimeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/18
 * @description: 富文本编辑器数据上传控制器
 */
@RestController
@RequestMapping("/editor")
@Api(tags = "EditorUploadController", description = "富文本编辑器数据上传操作api")
public class EditorUploadController extends BaseController {

    @Autowired
    private MyAdminProperties myAdminProperties;

    /**
     * 功能描述: 富文本编辑器图片上传
     *
     * @param image 上传图片
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/5/27 10:48
     */
    @ApiOperation(value = "富文本编辑器-图片上传", notes = "富文本编辑器图片上传")
    @ApiImplicitParam(name = "image", value = "富文本编辑器图片数据", required = true, dataType = "MultipartFile",
            paramType = "body")
    @RequestMapping(value = "/uploadEditorImage", method = RequestMethod.POST)
    public ResultData<Map<String, String>> uploadEditorImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            throw new MyAdminException("上传失败，为获取到上传图片!");
        }
        String fileName = "editorImage-" + ShiroUtil.getUser().getUserCode() + "-" + LocalDateTimeUtil.getMilliByTime(LocalDateTime.now());
        Map<String, String> result = FileUtil.uploadFile(image, myAdminProperties.getResourcePath(),
                myAdminProperties.getEditorImagePath(), fileName);

        if (Boolean.valueOf(result.get("state"))) {
            return new ResultData<Map<String, String>>(result);
        } else {
            throw new MyAdminException("上传失败");
        }
    }
}

package com.myadminmain.tools.generate.wrapper;

import com.common.wrapper.BaseControllerWrapper;
import com.myadminmain.tools.generate.entity.Generate;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/4/27
 * @description: user 对象包装类
 */
public class GenerateWrapper extends BaseControllerWrapper<Generate> {

    public GenerateWrapper(List<Generate> list) {
        super(list);
    }

    public GenerateWrapper(Generate obj) {
        super(obj);
    }

    @Override
    protected void wrapper(Generate data) {
        if (ObjectUtils.isNotEmpty(data.getQueryField())) {
            data.setQueryFields(Arrays.asList(data.getQueryField().split(",")));
        }
    }
}

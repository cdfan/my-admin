package com.common.wrapper;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * 功能描述: 控制器查询结果的包装类基类，用于对查询结果进行再次包装
 * 
 * @author cdfan
 * @date 2020/4/27 22:33
 */
public abstract class BaseControllerWrapper<T> {

    private List<T> list = null;
    private T obj = null;

    public BaseControllerWrapper(List<T> list) {
        this.list = list;
    }

    public BaseControllerWrapper(T obj) {
        this.obj = obj;
    }

    /**
     * 功能描述: 包装方法
     * 
     * @author cdfan
     * @date 2020/5/22 15:54
     */
    public void wrap() {
        if (ObjectUtils.isNotEmpty(this.list) || ObjectUtils.isNotEmpty(this.obj)) {
            if (ObjectUtils.isNotEmpty(this.list)) {
                for (T item : this.list) {
                    wrapper(item);
                }
            } else {
                wrapper(this.obj);
            }
        }
    }

    /**
     * 功能描述: 对数据进行包装，请实现当前方法
     * 
     * @param data 创建对的时候如果是list，则为list中的每一项，如果是Object，则为Object
     * @author cdfan
     * @date 2020/4/22 11:53
     */
    protected abstract void wrapper(T data);

}

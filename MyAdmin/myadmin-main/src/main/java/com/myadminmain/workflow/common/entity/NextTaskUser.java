package com.myadminmain.workflow.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/31
 * @description: 下一任务办理人对象
 */
@ApiModel(description = "下一任务办理人对象")
public class NextTaskUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否存在下一任务办理人")
    private Boolean hasNextUser = false;

    @ApiModelProperty(value = "下一任务办理人用户编码")
    private List<String> userCodes;

    public Boolean getHasNextUser() {
        return hasNextUser;
    }

    public void setHasNextUser(Boolean hasNextUser) {
        this.hasNextUser = hasNextUser;
    }

    public List<String> getUserCodes() {
        return userCodes;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }
}

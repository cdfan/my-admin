package com.common.annotion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述: 权限注解 用于检查权限 规定访问权限
 * @author cdfan
 * @date 2020/3/16 15:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {


    /**
     * 权限编码，
     * 如果有多个：
     *  在要求只要拥有其中某个权限就可以通过时：权限码之间通过 | 符号来连接
     *      例如：@Permission("role_edit|role_add")
     *  在要求拥有指定的所有权限才可以通过时：权限码之间通过 & 符号来连接
     *      例如：@Permission("role_edit&role_add")
     *  注意不支持|和&混用，因为好像不存在那种需求，而且官网的鉴权也未提供这种功能
     */
    String value() default "";

    /**
     * 角色英文名称
     * 使用注解时加上这个值表示限制必须要拥有某些角色的才可以访问对应的资源
     * 常用在某些资源限制只有超级管理员角色才可访问
     */
    String[] roles() default {};
}

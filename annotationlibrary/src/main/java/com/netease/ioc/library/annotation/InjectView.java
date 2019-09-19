package com.netease.ioc.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/9/19.
 */

@Target(ElementType.FIELD) // 作用在属性之上
@Retention(RetentionPolicy.RUNTIME) // jvm在运行时通过反射的技术，获取注解的值
public @interface InjectView {

    int value();// 获取控件的值

}

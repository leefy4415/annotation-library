package com.netease.ioc.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/9/19.
 */

@Target(ElementType.TYPE) // 该注解只能作用于类上
@Retention(RetentionPolicy.RUNTIME) // jvm在运行时通过反射的技术，获取注解的值
public @interface ContentView {

    //返回int类型的布局值
    int value();

}

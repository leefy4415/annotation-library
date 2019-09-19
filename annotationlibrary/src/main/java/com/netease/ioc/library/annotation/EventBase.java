package com.netease.ioc.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/9/19.
 */

@Target(ElementType.ANNOTATION_TYPE) // 该注解作用于另一个注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    // 1.setxxxListener
    String listenerSetter();

    // 2.监听的对象：new View.OnClickListener
    Class<?> listenerType();

    // 3.回调的方法：public void onclick(View v)
    String callbackListener();

}

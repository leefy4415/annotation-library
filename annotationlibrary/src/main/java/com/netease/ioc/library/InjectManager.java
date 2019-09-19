package com.netease.ioc.library;

import android.app.Activity;
import android.view.View;

import com.netease.ioc.library.annotation.ContentView;
import com.netease.ioc.library.annotation.EventBase;
import com.netease.ioc.library.annotation.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2019/9/2.
 */

public class InjectManager {

    public static void inject(Activity activity) {
        // 布局的注入
        injectLayout(activity);
        // 控件的注入
        injectViews(activity);
        // 事件的注入
        injectEvents(activity);
    }

    // 布局的注入
    private static void injectLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类之上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取ContentView注解的值
            int layoutId = contentView.value();
            // 1.直接调用setContentView方法
            /*if (layoutId > 0) {
                activity.setContentView(layoutId);
            }*/
            // 2.也可以反射获取setContentView方法
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                if (layoutId > 0) {
                    method.invoke(activity, layoutId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取所有的属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                // 获取注解的值
                int viewId = injectView.value();
                // 获取findViewById(id)方法
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    //赋值
                    field.setAccessible(true);// 将私有属性设置可以访问
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 事件的注入
    private static void injectEvents(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历方法
        for (Method method : methods) {
            // 遍历方法的所有注解
            Annotation[] annotations = method.getAnnotations();
            // 遍历每个注解
            for (Annotation annotation : annotations) {
                // 获取OnClick注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    // 事件的3个重要信息
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callbackListener = eventBase.callbackListener();
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 执行value方法，获取OnClick注解的值
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            // 代理的方式，外面是什么注解，就匹配对应的监听方法和回调方法
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callbackListener, method);
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                            //btn控件没有赋值，所以不能直接设置点击监听
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}

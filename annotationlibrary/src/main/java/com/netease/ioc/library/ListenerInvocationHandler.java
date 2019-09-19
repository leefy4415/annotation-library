package com.netease.ioc.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2019/9/19.
 */

public class ListenerInvocationHandler implements InvocationHandler {

    // 拦截的对象，MainActivity中本应该执行的onClick
    private Object target;
    // 键值对，key：onClick，value：开发者自定义的show(View view)方法
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (target != null) {
            String name = method.getName();
            // 如果集合中有需要执行拦截的方法名
            method = methodHashMap.get(name);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(target);
            }
        }
        return null;
    }

    /**
     * 拦截的方法，和替换执行的方法
     * @param methodName 拦截本应该执行的onClick方法
     * @param method 执行自定义的方法，show()
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}

package cn.homeron.homerfast.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtils {
    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @return 返回第一个类型
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @param 返回某下标的类型
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

}

package pers.fancy.frame4j.helper;

import java.lang.reflect.Field;
import java.util.Map;

import pers.fancy.frame4j.util.ReflectionUtil;
import pers.fancy.frame4j.annotation.Inject;
import pers.fancy.frame4j.util.ArrayUtil;
import pers.fancy.frame4j.util.CollectionUtil;

/**
 * IOC helper 反射与工厂模式
 * @author Fancy
 */
public final class IocHelper {

    private IocHelper(){}

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}

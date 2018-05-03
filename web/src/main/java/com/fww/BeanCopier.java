package com.fww;

import com.fww.utils.ClassUtil;
import net.sf.cglib.core.Converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @date 2018/04/29 20:40
 */
public class BeanCopier {
    private static Map<Integer, net.sf.cglib.beans.BeanCopier> CACHE = new ConcurrentHashMap<>();

    public BeanCopier() {
    }

    public static <T> T copy(Object from, Class<T> toClazz) {
        T to = ClassUtil.newInstance(toClazz);
        Class<?> soruceClazz = from.getClass();
        net.sf.cglib.beans.BeanCopier beanCopier = getBeanCopier(soruceClazz, toClazz);
        beanCopier.copy(from, to, (Converter)null);
        return to;
    }

    protected static net.sf.cglib.beans.BeanCopier getBeanCopier(Class<?> soruceClazz, Class<?> targetClazz) {
        int key = soruceClazz.hashCode() + targetClazz.hashCode();
        net.sf.cglib.beans.BeanCopier beanCopier = (net.sf.cglib.beans.BeanCopier)CACHE.get(Integer.valueOf(key));
        if(beanCopier == null) {
            beanCopier = createBeanCopier(soruceClazz, targetClazz);
        }

        return beanCopier;
    }

    protected static synchronized net.sf.cglib.beans.BeanCopier createBeanCopier(Class<?> soruceClazz, Class<?> targetClazz) {
        int key = soruceClazz.hashCode() + targetClazz.hashCode();
        net.sf.cglib.beans.BeanCopier beanCopier = (net.sf.cglib.beans.BeanCopier)CACHE.get(Integer.valueOf(key));
        if(beanCopier != null) {
            return beanCopier;
        } else {
            beanCopier = net.sf.cglib.beans.BeanCopier.create(soruceClazz, targetClazz, false);
            CACHE.put(Integer.valueOf(key), beanCopier);
            return beanCopier;
        }
    }
}

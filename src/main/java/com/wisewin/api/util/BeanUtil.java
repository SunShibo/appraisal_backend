package com.wisewin.api.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * Bean工具类
 *
 * @author Shibo Sun
 */
public class BeanUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    private static final Map<Class<?>, Class<? extends PropertyEditorSupport>> PROPERTY_EDITORS = new LinkedHashMap<Class<?>, Class<? extends PropertyEditorSupport>>();
    static {
        PROPERTY_EDITORS.put(byte[].class, ByteArrayMultipartFileEditor.class);
    }

    /**
     * 包装obj为BeanWapper对象
     * @param obj
     * @return
     */
    public static BeanWrapper forBeanPropertyAccess(Object obj) {
        BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        for (Map.Entry<Class<?>, Class<? extends PropertyEditorSupport>> entry : PROPERTY_EDITORS.entrySet()) {
            try {
                bean.registerCustomEditor(entry.getKey(), entry.getValue().newInstance());
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return bean;
    }

    /**
     * 复制 source对象中的属性到 target
     * @param source
     * @param target
     * @return
     */
    public static Object copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 复制 source对象中的属性到 target, 忽略指定属性的赋值
     * @param source
     * @param target
     * @param ignoreProperties
     * @return
     */
    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * 复制 source对象中的属性到 target
     * @param source
     * @param target
     * @return
     */
    public static Object copyProperties(Map source, Object target) {
        BeanWrapper targetBean = forBeanPropertyAccess(target);
        targetBean.setPropertyValues(source);
        return target;
    }

    /**
     * 复制 source对象中的属性到 target
     * @param source
     * @param target
     * @return
     */
    public static Map copyProperties(Map source, Map target) {
        if (source != null && target != null) {
            target.putAll(source);
        }

        return target;
    }

    public static Object getProperty(Object bean, String name) {
        if (bean == null) {
            return null;
        }

        try {
            return PropertyUtils.getNestedProperty(bean, name);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }

        return null;
    }

    public static String getPropertyAsString(Object bean, String name) {
        try {
            return BeanUtils.getProperty(bean, name);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }

        return null;
    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
    }
    public static void setProperty(Object o, Properties props) {
        for (Object keyObj : props.keySet()) {
            String key = (String) keyObj;
            try {
                BeanUtils.setProperty(o, key, props.getProperty(key));
            } catch (Exception e) {
                LOG.debug(e.getMessage(), e);
            }
        }
    }

    /**
     * 转换 obj对象为HashMap
     * @param obj
     * @return
     */
    public static Map toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            return (Map) obj;
        }
        else {
            Map map = new HashMap();
            BeanWrapper bean = new BeanWrapperImpl(obj);
            PropertyDescriptor[] pds = bean.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String name = pd.getName();
                if ("class".equals(name)) {
                    continue;
                }

                map.put(name, bean.getPropertyValue(name));
            }

            return map;
        }
    }

    /**
     * 将对象列表list转换为map对象， key为keyField属性值
     * @param list
     * @param keyField
     * @return
     */
    public static <T> Map<String, T> toMap(List<T> list, String keyField) {
        if (list == null) {
            return null;
        }

        Map<String, T> map = new HashMap<String, T>(list.size());
        for (T item : list) {
            map.put(getPropertyAsString(item, keyField), item);
        }

        return map;
    }

    /**
     * 将对象列表list转换为二位数组
     * @param list
     * @return
     */
    public static Object[][] toArray2(List list, String[] fields) {
        if (list == null || list.size() == 0
                || fields == null || fields.length == 0) {
            return null;
        }

        Object[][] result = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            Object itemObj = list.get(i);
            result[i] = new Object[fields.length];
            for (int j = 0; j < fields.length; j++) {
                result[i][j] = getProperty(itemObj, fields[j]);
            }
        }

        return result;
    }

    /**
     * 将对象列表list元素转换为数组对象
     * @param list
     * @return
     */
    public static List<Object[]> toArray(List list, String[] fields) {
        if (list == null || list.size() == 0
                || fields == null || fields.length == 0) {
            return null;
        }

        List<Object[]> result = new ArrayList<Object[]>(list.size());
        for (int i = 0; i < list.size(); i++) {
            Object itemObj = list.get(i);
            Object[] item = new Object[fields.length];
            for (int j = 0; j < fields.length; j++) {
                item[j] = getProperty(itemObj, fields[j]);
            }
            result.add(item);
        }

        return result;
    }

    public static List toTree(List nodeList) {
        return toTree(nodeList, "id", "parentId", "children", "leaf", true);
    }
    public static List toTreeByCode(List nodeList) {
        return toTree(nodeList, "code", "parentCode", "children", "leaf", true);
    }

    /**
     * 将list转换为树结构list
     * @param nodeList 所有树节点列表
     * @param nodeField 节点ID 字段名
     * @param parentNodeField 父节点ID 字段名
     * @param childrenField 子节点集合 字段名
     * @param leafField 叶子节点 字段名
     * @param clone 是否clone节点
     * @return
     */
    public static <T> List<T> toTree(List<T> nodeList, String nodeField, String parentNodeField, String childrenField, String leafField, boolean clone) {
        if (nodeList == null) {
            return null;
        }

        List<T> treeVOList = new ArrayList<T>();
        Map<String, List<Integer>> nodePaths = new HashMap<String, List<Integer>>() {
            @Override
            public List<Integer> get(Object key) {
                List<Integer> list = super.get(key);
                if (list == null) {
                    list = new ArrayList<Integer>();
                    super.put(key.toString(), list);
                }
                return list;
            }
        };

        for (T node : nodeList) {
            if (clone) {
                node = deepClone(node);
            }

            String nodeValue = getPropertyAsString(node, nodeField);
            String parentNodeValue = getPropertyAsString(node, parentNodeField);
            if (StringUtils.isBlank(parentNodeValue)) {
                if (StringUtils.isNotBlank(leafField)) {
                    setProperty(node, leafField, true);
                }

                int idx = treeVOList.size();
                treeVOList.add(idx, node);
                nodePaths.get(nodeValue).add(idx);
            } else {
                List<Integer> parentNodePath = nodePaths.get(parentNodeValue);
                if (parentNodePath.isEmpty()) {
                    continue;
                } else {
                    T pNodeVO = treeVOList.get(parentNodePath.get(0));
                    List<T> pNodeVOList = (List<T>) getProperty(pNodeVO, childrenField);
                    for (int i = 1; i < parentNodePath.size(); i++) {
                        pNodeVO = pNodeVOList.get(parentNodePath.get(i));
                        pNodeVOList = (List<T>) getProperty(pNodeVO, childrenField);
                    }

                    if (StringUtils.isNotBlank(leafField)) {
                        setProperty(pNodeVO, leafField, false);
                        setProperty(node, leafField, true);
                    }

                    int idx = pNodeVOList.size();
                    pNodeVOList.add(idx, node);

                    nodePaths.get(nodeValue).addAll(parentNodePath);
                    nodePaths.get(nodeValue).add(idx);
                }
            }
        }

        return treeVOList;
    }

    public static <T> T first(List<T> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * clone对象，不支持关联对象深度clone
     * @param obj
     * @return
     */
    public static <T> T clone(T obj) {
        if (obj == null) {
            return null;
        }

        try {
            return (T) BeanUtils.cloneBean(obj);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 深度clone对象
     * @param obj
     * @return
     */
    public static <T> T deepClone(T obj) {
        if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException("not implement Serializable interface");
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
            } finally {
                if (oos != null) {
                    oos.close();
                }
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                return (T) ois.readObject();
            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * 移除array数组中等于removeEl的元素
     * @param array
     * @param removeEl
     * @param toArrayObj
     * @return
     */
    private static <T> T[] removeElement(T[] array, T removeEl, Object[] toArrayObj) {
        if (array == null) {return null;};
        if (array.length == 0) {return array;}

        List<T> list = new ArrayList<T>(array.length);
        for (T t : array) {
            if (!((t == null && removeEl == null) || (t != null && removeEl != null && t.equals(removeEl)))) {
                list.add(t);
            }
        }

        return (T[]) list.toArray(toArrayObj);
    }
    public static String[] removeElement(String[] array, String removeEl) {
        return removeElement(array, removeEl, new String[0]);
    }

    /**
     * 过滤掉srcMap中key值不为prefix的元素
     * @param srcMap
     * @param prefix
     * @return
     */
    public static <V> Map<String,V> filterForPrefix(Map<String,V> srcMap, String prefix) {
        if (srcMap == null) return null;
        if (StringUtils.isBlank(prefix)) {
            return new HashMap<String, V>(srcMap);
        }

        Map<String,V> descMap = new HashMap<String,V>();
        for (Object keyObj : srcMap.keySet()) {
            String key = (String) keyObj;
            int prefixLen = prefix.length();
            if (key.startsWith(prefix)) {
                descMap.put(key.substring(prefixLen), srcMap.get(key));
            }
        }

        return descMap;
    }

    public static <T> Set<T> toSet(T[] array) {
        if (array == null) {
            return null;
        }

        Set<T> set = new LinkedHashSet<T>();
        for (T value : array) {
            set.add(value);
        }

        return set;
    }

    public static <T> List<T> toList(T[] array) {
        if (array == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();
        for (T value : array) {
            list.add(value);
        }

        return list;
    }

    /**
     * 复制source对象值不为null的属性到到target对象
     * @param source
     * @param target
     * @return
     */
    public static Object copyNotNullProperties(Object source, Object target) {
        copyProperties(source, target, new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                return value != null;
            }
        });

        return target;
    }

    /**
     * 根据filter规则，复制source对象值的属性到到target对象
     * @param source
     * @param target
     * @param
     * @return
     */
    public static Object copyProperties(Object source, Object target, PropertyFilter filter) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
//		if (editable != null) {
//			if (!editable.isInstance(target)) {
//				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
//						"] not assignable to Editable class [" + editable.getName() + "]");
//			}
//			actualEditable = editable;
//		}
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);

                            if (filter != null && !filter.apply(source, targetPd.getName(), value)) {
                                continue;
                            }

                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }

        return target;
    }

    public interface PropertyFilter {
        boolean apply(Object object, String name, Object value);
    }

}
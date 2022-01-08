package com.tusk.priest.util;


import javax.persistence.Id;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串处理工具
 *
 * @author alvin
 * @date 2019/3/28
 */
public class StringUtil {

    /**
     * 获取指定次数的空格符
     *
     * @param repeat 拼接次数
     */
    public static String blank(int repeat) {
        return repeat(" ", repeat);
    }

    /**
     * 重复拼接指定的字符串
     *
     * @param str    字符串
     * @param repeat 拼接次数
     */
    public static String repeat(String str, int repeat) {
        StringBuilder repeatStr = new StringBuilder("");
        for (int i = 0; i < repeat; i++) {
            repeatStr.append(str);
        }
        return repeatStr.toString();
    }

    /**
     * 匹配非java字符串下的字符
     */
    public static int matcher(String str, char input) {
        char[] value = str.toCharArray();
        int quotes = 0;
        for (int i = 0; i < str.length(); i++) {
            if (value[i] == '"') {
                if (++quotes == 2) {
                    quotes = 0;
                }
            }
            if (value[i] == input && quotes == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取字符串中的单词
     *
     * @param text   字符串
     * @param splits 自定义分割的字符
     * @return 单词列表
     */
    public static List<String> extWord(String text, Character... splits) {
        char[] chars = text.toCharArray();
        List<String> wordList = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        boolean section = true;
        for (char c : chars) {
            if (c == '<') {
                section = false;
            }
            if (c == '>') {
                section = true;
            }
            if (section && (c == 32 || c == 9 || ArrayContains(splits, c))) {
                if (word.length() > 0) {
                    wordList.add(word.toString());
                }
                word.delete(0, word.length());
            } else {
                word.append(c);
            }
        }
        if (word.length() > 0) {
            wordList.add(word.toString());
        }
        return wordList;
    }

    /**
     * 判断数组中是否存在某个元素
     *
     * @param arr 数组
     * @param c   元素
     */
    public static boolean ArrayContains(Object[] arr, Object c) {
        for (Object o : arr) {
            if (o.equals(c)) return true;
        }
        return false;
    }

    /**
     * Byte数组为空判断
     *
     * @param bytes
     * @return boolean
     * @author alvin
     * @date 2018/9/4 15:39
     */
    public static boolean isNull(byte[] bytes) {
        // 根据byte数组长度为0判断
        return bytes == null || bytes.length == 0;
    }

    /**
     * Byte数组不为空判断
     *
     * @param bytes
     * @return boolean
     * @author alvin
     * @date 2018/9/4 15:41
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }

    /**
     * 字符串为空判断
     *
     * @param str
     * @return boolean
     * @author alvin
     * @date 2018/9/4 15:41
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 获取属性值的类型
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static String getFieldTyp(Object obj, String fieldName) {
        String fieldTyp = "";
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            fieldTyp = field.getType().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldTyp;
    }

    /**
     * 获取对象get方法的值
     */
    public static Object getObjectMethod(Object obj, String filed) {
        try {
            Class<?> clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();// 获得get方法
            Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取对象主键 Id
     * 标注在方法上
     *
     * @param pd
     * @return
     */
    public static boolean isPrimaryKeyProperty(PropertyDescriptor pd) {
        Method getter = pd.getReadMethod();
        if (getter != null && getter.isAnnotationPresent(Id.class)) {
            return true;
        }
        Method setter = pd.getWriteMethod();
        if (setter != null && setter.isAnnotationPresent(Id.class)) {
            return true;
        }
        return false;
    }

    /**
     * 获取对象主键  Id
     * 标注在字段上
     *
     * @param pd
     * @param clazz
     * @return
     * @throws Exception
     */
    public static boolean isPrimaryKeyField(PropertyDescriptor pd, Class<?> clazz) throws Exception {
        Field field = clazz.getDeclaredField(pd.getName());
        if (field.isAnnotationPresent(Id.class)) {
            return true;
        }
        return false;
    }

    /**
     * 获取对象主键
     *
     * @param object
     * @return
     */
    public static List<String> getPrimaryKey(Object object) {
        List<String> primaryKeys = new ArrayList<String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                PropertyDescriptor pd = pds[i];
                // Id 标注在方法上
                if (isPrimaryKeyProperty(pd)) {
                    primaryKeys.add(pd.getName());
                    continue;
                }
                // Id 标注在字段上
                if (isPrimaryKeyField(pd, object.getClass())) {
                    primaryKeys.add(pd.getName());
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return primaryKeys;
    }



    public static String fullZero(int src, int length) {
        if (length < 1) return "0";
        if (String.valueOf(src).length() > length) return String.valueOf(src);
        return String.format("%0" + length + "d", src);
    }

    /**
     * 比较是否相同
     *
     * @param a
     * @param b
     * @return
     * @author alvin 2020年4月12日 下午3:38:26
     */
    public static boolean isNotEquals(String a, String b) {
        return !a.equals(b);
    }

    public static boolean isEquals(String a, String b) {
        if (StringUtil.isBlank(a)) {
            a = "";
        }
        if (StringUtil.isBlank(b)) {
            b = "";
        }
        return a.equals(b);
    }

    /**
     * 比较存在任意一个相同
     *
     * @param origin
     * @param targets
     * @return
     * @author alvin 2020年5月6日 下午3:38:26
     */
    public static boolean isEquals(String origin, String ... targets) {
        for (int i = 0; i < targets.length; i++) {
            if(StringUtil.isEquals(origin, targets[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEquals(Integer a, Integer b) {
        return a.equals(b);
    }


    /**
     * 前后两个集合是否用相同的值
     * */
    public static boolean isEqualsOne(List<String> aList, List<String> bList) {
        for(int i = 0; i <aList.size(); i++) {
            for(int j = 0; j <bList.size(); j++) {
                if(aList.get(i).equals(bList.get(j))) return true;
            }
        }

        return false;
    }



}

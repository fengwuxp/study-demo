package com.java.api.bean;

import com.java.api.bean.info.User;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by wuxp on 2016/8/22.
 * PropertyDescriptor类表示JavaBean类通过存储器导出一个属性。主要方法：
 * <p>
 * 1、getPropertyType()，获得属性的Class对象。
 * <p>
 * 2、getReadMethod()，获得用于读取属性值的方法；getWriteMethod()，获得用于写入属性值的方法。
 * <p>
 * 3、hashCode()，获取对象的哈希值。
 * <p>
 * 4、setReadMethod(Method readMethod)，设置用于读取属性值的方法；setWriteMethod(MethodwriteMethod)，设置用于写入属性值的方法；
 * <p>
 * 导包java.bean.*;
 * <p>
 */
public class PropertyDescriptorTest {

    public static void main(String[] args) throws Exception {

//        User user = new User();
//        PropertyDescriptor pd = new PropertyDescriptor("id", User.class);
//        Method methodGet = pd.getReadMethod();//Read对应get()方法
//       // Class c=pd.getPropertyType();
//        Object reValue = methodGet.invoke(user);
//
//        System.out.println(reValue);
//        System.out.println(reValue.getClass());
//
//        Method methodSet = pd.getWriteMethod();  //获取写的方法
//        methodSet.invoke(user, 100L);
//        reValue = methodGet.invoke(user);
//        System.out.println(reValue);
//
//        getProperty_2(user,"");
        //”,a,,b,”.split(“,”)
        String s=",a,,b,";
        String [] sl=s.split(",");
        System.out.println(Arrays.toString(sl));
    }

    private static Object getProperty_2(Object pt1, String propertyName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(pt1.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        Object reValue = null;
        for (PropertyDescriptor pd : pds) {
            /*if (pd.getName().equals(propertyName)) {
                Method methodGetX = pd.getReadMethod();
                reValue = methodGetX.invoke(pt1);
                break;
            }*/
            System.out.println(pd.getPropertyType()+" "+pd.getName());
        }
        return reValue;
    }
}
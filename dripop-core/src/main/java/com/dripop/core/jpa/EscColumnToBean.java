package com.dripop.core.jpa;

import org.hibernate.transform.ResultTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/** 
 * 自定义的数据库字库转换成POJO 
 */  
public class EscColumnToBean implements ResultTransformer {

    private Class<?> resultClass;

    private final static Set<Class> baseClass = new HashSet<Class>() {
        {
            add(Integer.class);
            add(BigInteger.class);
            add(Double.class);
            add(BigDecimal.class);
            add(Long.class);
            add(String.class);
            add(Date.class);
        }
    };

    public EscColumnToBean(Class<?> resultClass) {
        this.resultClass = resultClass;
    }

    public Object transformTuple(Object[] objects, String[] aliases) {
        if(baseClass.contains(resultClass)) {
            if(objects[0] instanceof BigInteger && resultClass != BigInteger.class) {
                return ((BigInteger)objects[0]).longValue();
            }
            if(objects[0] instanceof BigDecimal && resultClass != BigDecimal.class) {
                return ((BigDecimal)objects[0]).longValue();
            }
            return objects[0];
        }
        Method[] methods = resultClass.getDeclaredMethods();
        Method method = null;
        Object result = null;
        String methodName = null;
        Object val = null;
        try {
            result = resultClass.newInstance();
            for (int i = 0; i < aliases.length; i++) {
                methodName = "set" + aliases[i];
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].getName().toLowerCase().equals(methodName.replaceAll("_", "").toLowerCase())) {
                        method = methods[j];
                        val = objects[i];
                        if(val == null) {
                            continue;
                        }
                        if(val instanceof BigInteger && !methods[j].getParameterTypes()[0].getName().equals("java.math.BigInteger")) {
                            val = ((BigInteger)val).longValue();
                        }else if(val instanceof BigDecimal && !methods[j].getParameterTypes()[0].getName().equals("java.math.BigDecimal")) {
                            val = ((BigDecimal)val).longValue();
                        }
                        if(methods[j].getParameterTypes()[0].getName().equals("java.lang.String")){
                            method.invoke(result,new Object[] {val.toString()});
                        }else if(methods[j].getParameterTypes()[0].getName().equals("java.lang.Integer")){
                            method.invoke(result,new Object[] {new Integer(val.toString())});
                        }else {
                            method.invoke(result, new Object[] {val});
                        }
                        break;
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List transformList(List list) {
        return list;
    }
}
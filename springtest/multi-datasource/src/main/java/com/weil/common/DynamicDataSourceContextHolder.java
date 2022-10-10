package com.weil.common;

/**
 * @Name: DynamicDataSourceContextHolder
 * @Description: 动态数据源上下文
 * @Author: weil
 * @Date: 2022-10-10 19:20
 * @Version: 1.0
 */
public class DynamicDataSourceContextHolder {
    /**
     * 动态数据源名称上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置/切换数据源
     */
    public static void setContext(String ds){
        DATASOURCE_CONTEXT_HOLDER.set(ds);
    }

    /**
     * 获取数据源名称
     */
    public static String getContext(){
        String ds = DATASOURCE_CONTEXT_HOLDER.get();
        return ds == null?DynamicConstants.DS_MASTER:ds;
    }

    /**
     * 删除当前数据源名称
     */
    public static void removeContext(){
        DATASOURCE_CONTEXT_HOLDER.remove();
    }
}

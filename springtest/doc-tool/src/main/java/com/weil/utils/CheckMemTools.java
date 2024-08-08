package com.weil.utils;

/**
 * @Name: CheckMemTools
 * @Description:
 * @Author: weil
 * @Date: 2024-08-02 16:57
 * @Version: 1.0
 */
public class CheckMemTools {
    Runtime runtime = Runtime.getRuntime();

    private CheckMemTools() {
    }

    public static CheckMemTools getInstance() {
        return new CheckMemTools();
    }

    public void check() {
        // 获取总内存
        Long totalMemory = runtime.totalMemory();
        System.out.println("Total Memory: " + totalMemory + " bytes," + totalMemory / 1024 / 1024 + " MB");
        // 获取自由内存
        Long freeMemory = runtime.freeMemory();
        // 获取已使用内存
        Long usedMemory = totalMemory - freeMemory;
        System.out.println("Used Memory: " + usedMemory + " bytes," + usedMemory / 1024 / 1024 + " MB");
        // 获取最大可用内存
        Long maxMemory = runtime.maxMemory();
        System.out.println("Max Memory: " + maxMemory + " bytes," + maxMemory / 1024 / 1024 + " MB");
    }
}
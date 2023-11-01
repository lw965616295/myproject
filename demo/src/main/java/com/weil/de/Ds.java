package com.weil.de;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Name: Ds
 * @Description:
 * @Author: weil
 * @Date: 2023-06-15 09:09
 * @Version: 1.0
 */
public class Ds {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\svn\\ss");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                System.out.println(f.getName());
            }
            System.out.println("总数："+ files.length);
        }
    }
}

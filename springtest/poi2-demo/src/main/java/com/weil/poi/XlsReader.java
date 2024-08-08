package com.weil.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.util.Iterator;

public class XlsReader {
    public static void main(String[] args) {
        try {
            // 创建工作簿
            Workbook workbook = new HSSFWorkbook(new FileInputStream("C:\\Users\\weil\\Desktop\\xs.xls"));

            Iterator<Sheet> iterator = workbook.iterator();
            while (iterator.hasNext()) {
                Sheet sheet = iterator.next();
                // 遍历每一行
                for (Row row : sheet) {
                    // 遍历每一列
                    for (Cell cell : row) {
                        // 打印单元格内容
                        System.out.print(cell.toString() + "\t");
                    }
                    System.out.println();
                }
            }
            System.out.println("========单独获取第一个sheet=======");
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            
            // 遍历每一行
            for (Row row : sheet) {
                // 遍历每一列
                for (Cell cell : row) {
                    // 打印单元格内容
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }
            
            // 关闭工作簿
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
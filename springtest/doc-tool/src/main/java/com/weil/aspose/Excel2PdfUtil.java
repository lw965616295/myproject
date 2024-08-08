package com.weil.aspose;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * excel转换为pdf的工具类
 */
public class Excel2PdfUtil {

    /**
     * 许可证字符串
     */
    private static final String LICENSE = "<License>" +
            "<Data>" +
            "<Products><Product></Product><Product></Product></Products>" +
            "<EditionType></EditionType>" +
            "<SubscriptionExpiry></SubscriptionExpiry>" +
            "<LicenseExpiry></LicenseExpiry>" +
            "<SerialNumber></SerialNumber>" +
            "</Data>" +
            "<Signature></Signature>" +
            "</License>";

    /**
     * 设置 license 去除水印
     */
    private static void setLicense() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(LICENSE.getBytes());
        License license = new License();
        license.setLicense(byteArrayInputStream);
    }


    /**
     * excel 转 pdf
     *
     * @param excelFilePath excel文件路径
     * @param pdfFilePath   pdf文件路径
     * @param convertSheets 需要转换的sheet
     */
    public static void excelConvertPdf(String excelFilePath, String pdfFilePath, int[] convertSheets) {
        FileOutputStream fileOutputStream = null;
        try {
            pdfFilePath = pdfFilePath == null ? getPdfFilePath(excelFilePath) : pdfFilePath;
            // 设置License
            setLicense();
            // 读取excel文件
            Workbook wb = new Workbook(excelFilePath);
            fileOutputStream = new FileOutputStream(pdfFilePath);
            // 设置pdf格式
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            if (null != convertSheets) {
                printSheetPage(wb, convertSheets);
            }
            wb.save(fileOutputStream, pdfSaveOptions);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * excel 转 pdf 二进制流
     *
     * @param excelFilePath excel文件路径
     * @param pdfFilePath   pdf文件路径
     * @param convertSheets 需要转换的sheet
     */
    public static void excelConvertPdfByte(String excelFilePath, String pdfFilePath, int[] convertSheets) {
        FileOutputStream fileOutputStream = null;
        try {
            pdfFilePath = pdfFilePath == null ? getPdfFilePath(excelFilePath) : pdfFilePath;
            // 设置License
            setLicense();
            // 读取excel文件
            Workbook wb = new Workbook(excelFilePath);
            fileOutputStream = new FileOutputStream(pdfFilePath);
            // 设置pdf格式
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            if (null != convertSheets) {
                printSheetPage(wb, convertSheets);
            }
            wb.save(fileOutputStream, pdfSaveOptions);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取 生成的 pdf 文件路径，默认与源文件同一目录
     *
     * @param excelPath excel文件
     * @return 生成的 pdf 文件
     */
    private static String getPdfFilePath(String excelPath) {
        int lastIndexOfPoint = excelPath.lastIndexOf(".");
        String pdfFilePath = "";
        if (lastIndexOfPoint > -1) {
            pdfFilePath = excelPath.substring(0, lastIndexOfPoint);
        }
        return pdfFilePath + ".pdf";
    }


    /**
     * 隐藏workbook中不需要的sheet页。
     *
     * @param sheets 显示页的sheet数组
     */
    private static void printSheetPage(Workbook wb, int[] sheets) {
        // 隐藏非第一个sheet页
        for (int i = 1; i < wb.getWorksheets().getCount(); i++) {
            wb.getWorksheets().get(i).setVisible(false);
        }
        // 设置显示的sheet页
        if (null == sheets || sheets.length == 0) {
            wb.getWorksheets().get(0).setVisible(true);
        } else {
            for (int i = 0; i < sheets.length; i++) {
                wb.getWorksheets().get(i).setVisible(true);
            }
        }
    }


    public static void main(String[] args) {
        Excel2PdfUtil.excelConvertPdf("C:\\Users\\weil\\Desktop\\xs.xlsx", "C:\\Users\\weil\\Desktop\\out\\xs.pdf", null);
    }
}

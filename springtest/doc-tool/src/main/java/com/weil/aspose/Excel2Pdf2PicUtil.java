package com.weil.aspose;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * excel转换为pdf再转图片的工具类
 */
public class Excel2Pdf2PicUtil {

    /**
     * 许可证字符串
     */
    private static final String LICENSE = "<License>" +
            "<Data>" +
            "<Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products>" +
            "<EditionType>Enterprise</EditionType>" +
            "<SubscriptionExpiry>20991231</SubscriptionExpiry>" +
            "<LicenseExpiry>20991231</LicenseExpiry>" +
            "<SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>" +
            "</Data>" +
            "<Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>" +
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
     * excel 转 pdf 再转图片
     *
     * @param excelFilePath excel文件路径
     * @param picPath   图片文件路径
     * @param convertSheets 需要转换的sheet
     */
    public static void excelConvertPdf(String excelFilePath, String picPath, int[] convertSheets) {
        ByteArrayOutputStream bos = null;
        try {
            // 设置License
            setLicense();
            // 读取excel文件
            Workbook wb = new Workbook(excelFilePath);
            bos = new ByteArrayOutputStream();
            // 设置pdf格式
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            if (null != convertSheets) {
                printSheetPage(wb, convertSheets);
            }
            wb.save(bos, pdfSaveOptions);

            // bos转成bis
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

            // 使用pdfbox获取首页图
            PDDocument document = PDDocument.load(bis);
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 300); // 渲染PDF页面为图片，DPI为300
            ImageIO.write(image, "JPG", new File(picPath)); // 保存图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert bos != null;
                bos.close();
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
        Excel2Pdf2PicUtil.excelConvertPdf("C:\\Users\\weil\\Desktop\\xs.xlsx", "C:\\Users\\weil\\Desktop\\xs.jpg", null);
    }
}

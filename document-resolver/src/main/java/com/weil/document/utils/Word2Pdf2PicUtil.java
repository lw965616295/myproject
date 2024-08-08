package com.weil.document.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * word转pdf再转图片工具类
 */
public class Word2Pdf2PicUtil {

    /**
     * 许可证字符串(可以放到resource下的xml文件中也可)
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
        try {
            license.setLicense(byteArrayInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * word 转 pdf 生成至指定路径，pdf为空则上传至word同级目录
     *
     * @param in word文件输入
     * @param picPath  pic文件路径
     */
    public static void wordConvertPdfFile(InputStream in, String picPath) {
        PDDocument document = null;
        try {
//            in.reset();
            setLicense();
            // 设置中间临时文件pdf字节
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document doc = new Document(in);
            doc.save(byteArrayOutputStream, SaveFormat.PDF);

            // 使用pdfbox获取首页图
            document = PDDocument.load(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            PDFRenderer renderer = new PDFRenderer(document);
            // 渲染PDF页面为图片，DPI为300
            BufferedImage image = renderer.renderImageWithDPI(0, 300);
            // 保存图片
            ImageIO.write(image, "JPG", new File(picPath));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * word 转 pdf 生成byte字节流
     *
     * @param wordPath word所在的目录地址
     * @return
     */
    public static byte[] wordConvertPdfByte(String wordPath) {
        ByteArrayOutputStream fileOutputStream = null;
        try {
            setLicense();
            fileOutputStream = new ByteArrayOutputStream();
            Document doc = new Document(wordPath);
            doc.save(fileOutputStream, SaveFormat.PDF);
            return fileOutputStream.toByteArray();
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
        return null;
    }


    public static void main(String[] args) throws Exception{
        InputStream in = new FileInputStream("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx");
        Word2Pdf2PicUtil.wordConvertPdfFile(in,"C:\\Users\\weil\\Desktop\\success.jpg");
    }
}


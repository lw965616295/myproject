package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.Excel2Pdf2PicUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

/**
 * @Name: XlsxResolver
 * @Description: xlsx文件解析
 * @Author: weil
 * @Date: 2024-07-30 17:28
 * @Version: 1.0
 */
public class XlsxResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 存放解析全文文本
        StringBuilder sb = new StringBuilder();
        try (Workbook workbook = new XSSFWorkbook(param.getInputStream())) {
            Iterator<Sheet> iterator = workbook.iterator();
            while (iterator.hasNext()) {
                Sheet sheet = iterator.next();
                // 遍历每一行
                for (Row row : sheet) {
                    // 遍历每一列
                    for (Cell cell : row) {
                        // 打印单元格内容
                        sb.append(cell.toString()).append("\t");
                    }
                    sb.append(System.lineSeparator());
                }
            }
            textModel.setFullText(sb.toString());
            // 重置字节数组流
            param.getInputStream().reset();
        }catch (Exception e){
            e.printStackTrace();
        }
        return textModel;
    }


    @Override
    protected String homepageResolve(ResolveParam param) {
        // 当前先使用aspose方式生成首页图片
        String picPath = "C:\\Users\\weil\\Desktop\\success.jpg";
        Excel2Pdf2PicUtil.excelConvertPdf(param.getInputStream(), picPath, null);
        return picPath;
    }
}

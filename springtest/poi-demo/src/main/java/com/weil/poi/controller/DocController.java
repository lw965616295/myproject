package com.weil.poi.controller;

import com.weil.poi.common.WordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @ClassName DocController
 * @Author weil
 * @Description //doc文件操作
 * @Date 2022/6/9 15:37
 * @Version 1.0.0
 **/
@Slf4j
@RestController
@RequestMapping("/doc")
public class DocController {
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception{
        String auditName = "小李";
        String systemName = "es系统";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
        String date = format1.format(new Date());
        LocalDate localDate = LocalDate.now().minusDays(30);
        String dateRange = localDate.getYear()+"年"+localDate.getMonthValue()+"月"+localDate.getDayOfMonth()+"日—"+date;
        String totalSize = "10";

        Map<String, Object> params = new HashMap<>();
        params.put("auditName", auditName);
        params.put("systemName", systemName);
        params.put("date", date);
        params.put("dateRange", dateRange);
        params.put("totalSize", totalSize);

        // 饼图数据
        String[] senTypes = new String[]{"敏感", "较敏感", "一般敏感", "不敏感"};
        Integer[] typeCounts = new Integer[]{1, 50, 12, 33};
        // 柱状图
        String[] interfaceNames = new String[]{"aa", "bb", "cc", "dd", "ee"};
        Map<String, Number[]> map = new HashMap<>();
        map.put(null, new Integer[]{100, 90, 40, 15, 1});
        // 表格
        List<String[]> tableTitle = new ArrayList<>();
        tableTitle.add(new String[]{"访问时间", "接口名称", "接口URL", "接口操作", "系统名称", "系统IP/端口", "敏感数据访问量"});
        List<String[]> table = new ArrayList<>();
        table.add(new String[]{"2020/04/06 09:46:54", "AAA", "192.168.0.1", "登录", "ccc", "78.24.15.1/80", "52412"});
        table.add(new String[]{"2020/05/03 10:12:33", "BBB", "192.168.3.13", "注册", "ccc", "78.24.15.1/80", "22"});
        table.add(new String[]{"2021/02/07 05:32:12", "CCC", "192.168.4.32", "登录", "ccc", "78.24.15.1/80", "35"});
        // 饼图数据
        String[] riskTypes = new String[]{"高风险", "中风险", "低风险", "无风险"};
        Integer[] riskCounts = new Integer[]{1, 50, 12, 33};

//        String inPath = System.getProperty("user.dir") + "\\poi_demo\\src\\main\\resources\\template\\日志审计报告.docx";
        // 获取模板文件
        File file = ResourceUtils.getFile("classpath:template/日志审计报告.docx");
        try (InputStream is = new FileInputStream(file);
//             OutputStream os = new FileOutputStream(outPath);
             XWPFDocument document = new XWPFDocument(OPCPackage.open(is))) {
            WordUtils wordUtils = new WordUtils();
            // 替换段落中的参数
            wordUtils.replaceInPara(document, params);

            // 接口日志类型统计图
            wordUtils.createTitle(document.createParagraph(), "一、\t接口日志类型统计图");
            wordUtils.createPieChart(document, "接口日志类型统计图", senTypes, typeCounts);

            // 敏感访问次数统计TOP10
            wordUtils.createTitle(document.createParagraph(), "二、\t敏感访问次数统计TOP10");
            wordUtils.createBarChart(document, "敏感访问次数统计TOP10", null, null, interfaceNames, map);

            // 敏感URL访问列表
            wordUtils.createTitle(document.createParagraph(), "三、\t敏感URL访问列表");
            wordUtils.createTable(document, tableTitle);
            wordUtils.insertTableRow(document.getTableArray(0), table);

            // 接口风险等级统计图
            wordUtils.createTitle(document.createParagraph(), "四、\t接口风险等级统计图");
            wordUtils.createPieChart(document, "接口风险等级统计图", riskTypes, riskCounts);

            // 下载配置
            response.setHeader("Content-Type","application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("日志审计报告.docx", "utf-8"));
            ServletOutputStream out = response.getOutputStream();
            document.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



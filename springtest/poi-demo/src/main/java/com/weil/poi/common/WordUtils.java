package com.weil.poi.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtils {

    /**
     * 替换文档中的内容
     *
     * @param doc    Word的文档
     * @param params 待填充的数据
     *               params.put("key",value) 文档中对应为 ${key}
     */
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        while (iterator.hasNext()) {
            replaceInPara(iterator.next(), params);
        }
    }


    /**
     * 遍历所有表格，替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInAllTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        while (iterator.hasNext()) {
            replaceInTable(iterator.next(), params);
        }
    }

    /**
     * 替换指定表格中的变量
     *
     * @param doc
     * @param tabIndex 表格下标
     * @param params   替换参数
     */
    public void replaceInTable(XWPFDocument doc, int[] tabIndex, Map<String, Object> params) {
        List<XWPFTable> tables = doc.getTables();
        for (int index : tabIndex) {
            replaceInTable(tables.get(index), params);
        }
    }

    /**
     * 替换表格中的变量
     *
     * @param table
     * @param params
     */
    public void replaceInTable(XWPFTable table, Map<String, Object> params) {
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        //判断表格中是否有 ${} 有就表示需要替换值
        if (matcher(table.getText()).find()) {
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 在表格中新增行数并填充数据
     *
     * @param table    需要插入数据的表格
     * @param rowDatas 插入数据集合（注：填充的数据要与单元格的数量保持一致）
     */
    public void insertTableRow(XWPFTable table, List<String[]> rowDatas) {
        for (String[] cellDatas : rowDatas) {
            XWPFTableRow row = table.createRow();
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                cells.get(j).setText(cellDatas[j]);
            }
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        StringBuilder runText = new StringBuilder();

        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int j = runs.size();
            for (int i = 0; i < j; i++) {
                runText.append(runs.get(0).toString());
                //保留最后一个段落，在这段落中替换值，保留原有段落样式
                if (!((j - 1) == i)) {
                    para.removeRun(0);
                }
            }
            String text = runText.toString();
            Matcher matcher;
            while ((matcher = matcher(text)).find()) {
                text = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
            }
            runs.get(0).setText(text, 0);

        }
    }

    /**
     * 创建标题
     *
     * @param paragraph
     * @param data
     */
    public void createTitle(XWPFParagraph paragraph, String data) {
        XWPFRun createRun = paragraph.insertNewRun(0);
        createRun.setText(data);
        createRun.setFontFamily("宋体");
        createRun.setFontSize(14);
        createRun.setBold(true);
        paragraph.setSpacingAfter(10);
        paragraph.setSpacingBefore(10);
        //对齐方式
        paragraph.setAlignment(ParagraphAlignment.LEFT);
    }

    /**
     * 创建段落
     *
     * @param paragraph
     * @param data
     */
    public void createParagraph(XWPFParagraph paragraph, String data) {
        XWPFRun createRun = paragraph.createRun();
        createRun.setText(data);
        createRun.setFontFamily("宋体");
        createRun.setFontSize(12);

        paragraph.setFirstLineIndent(20);
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setIndentationFirstLine(600);
        paragraph.setSpacingAfter(10);
        paragraph.setSpacingBefore(10);
    }

    /**
     * 创建表格
     *
     * @param document
     * @param tableList
     */
    public void createTable(XWPFDocument document, List<String[]> tableList) {
        XWPFTable table = document.createTable(tableList.size(), tableList.get(0).length);
        //设置表格的宽度
        CTTblWidth comTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.PCT);
        comTableWidth.setW(BigInteger.valueOf(5000));

        // 填充数据
        int length = tableList.size();
        for (int i = 0; i < length; i++) {
            XWPFTableRow row = table.getRow(i);
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                cells.get(j).setText(tableList.get(i)[j]);
            }
        }

    }


    /**
     * 饼图
     *
     * @param document
     * @param title      图的标题
     * @param valueTitle 图种类名称
     * @param values     图种类的值
     */
    public void createPieChart(XWPFDocument document, String title, String[] valueTitle, Integer[] values) throws IOException, InvalidFormatException {

        XWPFChart chart = document.createChart(15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);
        // 标题
        chart.setTitleText(title);
        // 标题是否覆盖图表
        chart.setTitleOverlay(false);

        // 图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFCategoryDataSource categorys = XDDFDataSourcesFactory.fromArray(valueTitle);

        XDDFNumericalDataSource<Integer> numerical = XDDFDataSourcesFactory.fromArray(values);

        XDDFChartData data = chart.createData(ChartTypes.PIE, null, null);
        // 设置为可变颜色
        data.setVaryColors(true);
        // 图表加载数据
        data.addSeries(categorys, numerical);
        // 绘制
        chart.plot(data);

        CTDLbls dLbls = chart.getCTChart().getPlotArea().getPieChartArray(0).getSerArray(0).addNewDLbls();
        dLbls.addNewShowVal().setVal(false);//不显示值
        dLbls.addNewShowLegendKey().setVal(false);
        dLbls.addNewShowCatName().setVal(true);//类别名称
        dLbls.addNewShowSerName().setVal(false);//不显示系列名称
        dLbls.addNewShowPercent().setVal(true);//显示百分比
        dLbls.addNewShowLeaderLines().setVal(true); //显示引导线

    }

    /**
     * 柱状图
     *
     * @param document
     * @param title      标题
     * @param xAxisTitle X轴标题
     * @param yAxisTitle Y轴标题
     * @param categorys  种类
     * @param values     值
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void createBarChart(XWPFDocument document, String title, String xAxisTitle, String yAxisTitle, String[] categorys, Map<String, Number[]> values) throws IOException, InvalidFormatException {

        XWPFChart chart = document.createChart(15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);
        // 标题
        chart.setTitleText(title);
        // 标题覆盖
        chart.setTitleOverlay(false);
        // 图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP);
        legend.setOverlay(true);

        //X轴属性
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        xAxis.setTitle(xAxisTitle);

        // Y轴属性
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
        yAxis.setTitle(yAxisTitle);
        yAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        yAxis.setCrossBetween(AxisCrossBetween.BETWEEN);

        XDDFChartData data = chart.createData(ChartTypes.BAR, xAxis, yAxis);
        data.setVaryColors(true);
        ((XDDFBarChartData) data).setBarDirection(BarDirection.COL); // 设置方向为竖状

        XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromArray(categorys);
        values.forEach((k, v) -> {
            XDDFChartData.Series series = data.addSeries(categoriesData, XDDFDataSourcesFactory.fromArray(v));
            series.setTitle(k, null);
        });
        chart.plot(data);


    }


    /**
     * 柱状图
     *
     * @param document
     * @param title      标题
     * @param xAxisTitle X轴标题
     * @param yAxisTitle Y轴标题
     * @param categorys  种类
     * @param values     值
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void createLineChart(XWPFDocument document, String title, String xAxisTitle, String yAxisTitle, String[] categorys, Map<String, Number[]> values) throws IOException, InvalidFormatException {
        XWPFChart chart = document.createChart(15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);

        // 标题
        chart.setTitleText(title);
        // 标题覆盖
        chart.setTitleOverlay(false);
        // 图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP);
        legend.setOverlay(true);

        //X轴属性
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        xAxis.setTitle(xAxisTitle);

        //Y轴属性
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
        yAxis.setTitle(yAxisTitle);

        // 折线图，
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, xAxis, yAxis);

        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categorys);
        // 加载数据
        values.forEach((k, v) -> {
            XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(countries, XDDFDataSourcesFactory.fromArray(v));
            series.setTitle(k, null); // 折线图例标题
            series.setMarkerStyle(MarkerStyle.CIRCLE); // 设置标记样式
        });
        // 绘制
        chart.plot(data);
    }

    /**
     * 柱状图
     *
     * @param document
     * @param title      标题
     * @param xAxisTitle X轴标题
     * @param yAxisTitle Y轴标题
     * @param categorys  种类
     * @param values     值
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void createBarLineChart(XWPFDocument document, String title, String xAxisTitle, String yAxisTitle, String[] categorys, Map<String, Number[]> values) throws IOException, InvalidFormatException {
        XWPFChart chart = document.createChart(15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);

        // 标题
        chart.setTitleText(title);
        // 标题覆盖
        chart.setTitleOverlay(false);
        // 图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP);
        legend.setOverlay(true);

        //X轴属性
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        xAxis.setTitle(xAxisTitle);

        //Y轴属性
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
        yAxis.setTitle(yAxisTitle);
        yAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        yAxis.setCrossBetween(AxisCrossBetween.BETWEEN);

        // 折线图，
        XDDFLineChartData lineData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, xAxis, yAxis);
        // 柱状图
        XDDFChartData barData = chart.createData(ChartTypes.BAR, xAxis, yAxis);
        barData.setVaryColors(true);
        ((XDDFBarChartData) barData).setBarDirection(BarDirection.COL); // 设置方向为竖状

        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categorys);
        // 加载数据
        values.forEach((k, v) -> {
            XDDFChartData.Series barSeries = barData.addSeries(countries, XDDFDataSourcesFactory.fromArray(v));
            barSeries.setTitle(k, null);
            XDDFLineChartData.Series lineSeries = (XDDFLineChartData.Series) lineData.addSeries(countries, XDDFDataSourcesFactory.fromArray(v));
            lineSeries.setTitle(k, null); // 折线图例标题
            lineSeries.setMarkerStyle(MarkerStyle.CIRCLE); // 设置标记样式
        });
        // 绘制
        chart.plot(lineData);
        chart.plot(barData);
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }
}

package com.weil.tenxun.map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Name: KeywordPromptReq
 * @Description: 关键词输入提示请求参数
 * @Author: weil
 * @Date: 2023-11-03 09:07
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class KeywordPromptReq {
    /**
     * 开发密钥（Key）
     */
    private String key;
    /**
     * 用户输入的关键词（希望获取后续提示的关键词）
     */
    private String keyword;
    /**
     * 根据城市名称限制地域范围， 如，仅获取“广州市”范围内的提示内容；
     * 缺省时侧进行全国范围搜索；
     */
    private String region;
    /**
     * 0：[默认] 不限制当前城市，会召回其他城市的poi
     * 1：仅限制在当前城市
     */
    private String region_fix;
    /**
     * 定位坐标，传入后，若用户搜索关键词为类别词（如酒店、餐馆时），与此坐标距离近的地点将靠前显示，格式： location=lat,lng
     */
    private String location;
    /**
     * 是否返回子地点，如大厦停车场、出入口等取值：
     * 0 [默认]不返回
     * 1 返回
     */
    private String get_subpois;
    /**
     * 检索策略，目前支持：
     * policy=0：默认，常规策略
     * policy=1：本策略主要用于收货地址、上门服务地址的填写，
     * 提高了小区类、商务楼宇、大学等分类的排序，过滤行政区、
     * 道路等分类（如海淀大街、朝阳区等），排序策略引入真实用户对输入提示的点击热度，
     * 使之更为符合此类应用场景，体验更为舒适
     * policy=10：出行场景（网约车） – 起点查询
     * policy=11：出行场景（网约车） – 终点查询
     */
    private String policy;
    /**
     * 筛选条件：
     * 基本语法：columnName<筛选列>=value<列值>；
     * 目前支持按POI分类筛选（例：category=分类词），若指定多个分类用英文逗号分隔，最多支持五个分类,支持的分类词可参考：附录：腾讯地图POI分类关键词
     */
    private String filter;
    /**
     * 返回指定标准附加字段，取值支持：
     * category_code - poi分类编码
     */
    private String added_fields;
    /**
     * 可选值：short
     * 返回“不带行政区划的”短地址
     */
    private String address_format;
    /**
     * 页码，从1开始，最大页码需通过count进行计算，必须与page_size同时使用
     */
    private String page_index;
    /**
     * 每页条数，取值范围1-20，必须与page_index 同时使用
     */
    private String page_size;
    /**
     * 返回格式：支持JSON/JSONP，默认JSON
     */
    private String output;
    /**
     * JSONP方式回调函数
     */
    private String callback;
}

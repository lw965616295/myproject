package com.weil.tenxun.map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Name: ReverseAddressReq
 * @Description: 逆地址解析请求参数
 * @Author: weil
 * @Date: 2023-11-03 11:21
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class ReverseAddressReq {
    /**
     * 开发密钥（Key）
     */
    private String key;
    /**
     * 经纬度（GCJ02坐标系），格式：
     * location=lat<纬度>,lng<经度>
     */
    private String location;
    /**
     * 是否返回周边地点（POI）列表，可选值：
     * 0 不返回(默认)
     * 1 返回
     */
    private String get_poi;
    /**
     * 周边POI（AOI）列表控制参数：
     * 1 poi_options=address_format=short
     * 返回短地址，缺省时返回长地址
     * 2 poi_options=radius=5000
     * 半径，取值范围 1-5000（米）
     * 3 poi_options=policy=1/2/3/4/5
     * 控制返回场景，
     * policy=1[默认] 以地标+主要的路+近距离POI为主，着力描述当前位置；
     * policy=2 到家场景：筛选合适收货的POI，并会细化收货地址，精确到楼栋；
     * policy=3 出行场景：过滤掉车辆不易到达的POI(如一些景区内POI)，增加道路出入口、交叉口、大区域出入口类POI，排序会根据真实API大用户的用户点击自动优化。
     * policy=4 社交签到场景，针对用户签到的热门 地点进行优先排序。
     * policy=5 位置共享场景，用户经常用于发送位置、位置分享等场景的热门地点优先排序
     * 4 注：policy=1/2/3最多返回10条周边POI，policy=4/5最多返回20条，
     * 如需更多请参见地点搜索-周边推荐
     */
    private String poi_options;
    /**
     * 返回格式：支持JSON/JSONP，默认JSON
     */
    private String output;
    /**
     * JSONP方式回调函数
     */
    private String callback;
}

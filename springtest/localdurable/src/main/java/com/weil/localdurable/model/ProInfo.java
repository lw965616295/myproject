package com.weil.localdurable.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Name: ProInfo
 * @Description: 项目信息
 * @Author: weil
 * @Date: 2022-09-06 10:14
 * @Version: 1.0
 */
@Data
public class ProInfo implements Serializable {
    private String proName;
    private Date startDate;
    private Date endDate;
    private Integer rate;
}

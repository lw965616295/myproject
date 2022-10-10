package com.weil.config;

import com.weil.common.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Name: DynamicDataSource
 * @Description:
 * @Author: weil
 * @Date: 2022-10-10 17:51
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getContext();
    }
}

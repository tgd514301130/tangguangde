package com.goode.loader.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 期数类型<br>
 * 期数类型（YEAR:年、MONTH:月、DAY:日）<br>
 * 应用的表：pmc_cost、pmc_product、pmc_product_version、pmc_rate_rule、pmc_rate_rule_version
 *
 * @Date 2018/6/20 16:57
 */
public enum PeriodsTypeEnum {
    /**
     * 年
     */
    YEAR,
    /**
     * 月
     */
    MONTH,
    /**
     * 日
     */
    DAY;

    /**
     * 获取描述<br>
     *
     * @Date 2018/6/14 17:36
     */
    public String getName() {
        switch (this) {
            case YEAR:
                return "年";
            case MONTH:
                return "月";
            case DAY:
                return "日";
            default:
                return "";
        }
    }

    /**
     * 枚举转map<br>
     *
     * @Date 2018/6/14 17:35
     */
    public static Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (PeriodsTypeEnum type : PeriodsTypeEnum.values()) {
            map.put(type.toString(), type.getName());
        }
        return map;
    }
}

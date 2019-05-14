package com.goode.loader.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 费用收取时机<br>
 * 费用收取时机（MONTH_BEGIN_CHARGE:按月期初收取、MONTH_END_CHARGE:按月期末收取、SINGLE_USE_CHARGE:一次性收取）<br>
 * 应用的表：
 * pmc_cost
 * pmc_cost_version
 *
 * @Date 2018/6/20 21:05
 */
public enum CostChargeChanceEnum {
    /**
     * 按月期初收取
     */
    MONTH_BEGIN_CHARGE,
    /**
     * 按月期末收取
     */
    MONTH_END_CHARGE,
    /**
     * 一次性收取
     */
    SINGLE_USE_CHARGE;

    /**
     * 获取描述<br>
     *
     * @Date 2018/6/14 17:36
     */
    public String getName() {
        switch (this) {
            case MONTH_BEGIN_CHARGE:
                return "按月期初收取";
            case MONTH_END_CHARGE:
                return "按月期末收取";
            case SINGLE_USE_CHARGE:
                return "一次性收取";
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
        for (CostChargeChanceEnum type : CostChargeChanceEnum.values()) {
            map.put(type.toString(), type.getName());
        }
        return map;
    }
}

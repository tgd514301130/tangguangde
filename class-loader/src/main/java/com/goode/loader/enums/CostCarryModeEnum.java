package com.goode.loader.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 进位方式<br>
 * 进位方式（TWO_DECIMALS_AFTER_ROUND:保留两位小数，后一位四舍五入；TWO_DECIMALS_AFTER_Carry:保留两位小数，后一位进位；TWO_DECIMALS_AFTER_reject:保留两位小数，后一位直接舍弃）<br>
 * 应用的表：
 * pmc_overdue_rule
 * pmc_overdue_rule_version
 * pmc_default_interest_rule
 * pmc_default_interest_rule_version
 * pmc_cost
 * pmc_cost_version
 *
 * @Date 2018/6/20 20:37
 */
public enum CostCarryModeEnum {

    /**
     * 保留两位小数，后一位四舍五入
     */
    TWO_DECIMALS_AFTER_ROUND,
    /**
     * 保留两位小数，后一位进位
     */
    TWO_DECIMALS_AFTER_CARRY,
    /**
     * 保留两位小数，后一位直接舍弃
     */
    TWO_DECIMALS_AFTER_REJECT;

    /**
     * 获取描述<br>
     *
     * @Date 2018/6/14 17:36
     */
    public String getName() {
        switch (this) {
            case TWO_DECIMALS_AFTER_ROUND:
                return "保留两位小数，后一位四舍五入";
            case TWO_DECIMALS_AFTER_CARRY:
                return "保留两位小数，后一位进位";
            case TWO_DECIMALS_AFTER_REJECT:
                return "保留两位小数，后一位直接舍弃";
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
        for (CostCarryModeEnum type : CostCarryModeEnum.values()) {
            map.put(type.toString(), type.getName());
        }
        return map;
    }
}

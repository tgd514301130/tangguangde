package com.goode.loader.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 费用收取方式<br>
 * 费用收取方式（FIXED_RATIO:按固定比例；FIXED_AMOUNT:按固定金额；）<br>
 * 应用的表：
 * pmc_overdue_rule_config
 * pmc_overdue_rule_config_version
 * pmc_cost_extend
 *
 * @Date 2018/6/20 20:48
 */
public enum CostChargeModeEnum {
    /**
     * 按固定比例
     */
    FIXED_RATIO,
    /**
     * 按固定金额
     */
    FIXED_AMOUNT;

    /**
     * 获取描述<br>
     *
     * @Date 2018/6/14 17:36
     */
    public String getName() {
        switch (this) {
            case FIXED_RATIO:
                return "按固定比例";
            case FIXED_AMOUNT:
                return "按固定金额";
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
        for (CostChargeModeEnum type : CostChargeModeEnum.values()) {
            map.put(type.toString(), type.getName());
        }
        return map;
    }
}

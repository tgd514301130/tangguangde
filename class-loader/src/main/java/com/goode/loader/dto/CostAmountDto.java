package com.goode.loader.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostAmountDto {
    /**
     * 起始期限
     */
    private Integer startPeriods;
    /**
     * 结束期限
     */
    private Integer endPeriods;
    /**
     * 收取方式名称
     */
    private String chargeModeName;

    /**
     * 费率/固定金额
     */
    private BigDecimal rateAmount;
    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 赋值<br>
     *
     * @param costConfigExtendParamDto 费用配置参数
     * @return 费用
     * @date 2018/8/4 14:46
     */
    public CostAmountDto setValue(CostConfigExtendParamDto costConfigExtendParamDto) {
        this.setStartPeriods(costConfigExtendParamDto.getStartPeriods());
        this.setEndPeriods(costConfigExtendParamDto.getEndPeriods());
        this.setChargeModeName(costConfigExtendParamDto.getChargeModeName());
        this.setRateAmount(costConfigExtendParamDto.getRateAmount());
        return this;
    }

}

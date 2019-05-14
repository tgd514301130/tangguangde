package com.goode.loader.dto;

import com.goode.loader.enums.CostChargeModeEnum;
import lombok.Data;

import java.util.Map;

/**
 * 费用配置扩展参数<br>
 * <br>
 *
 * @Date 2018/7/13 14:07
 */
@Data
public class CostConfigExtendParamDto {

    /**
     * 起始期数（月）
     */
    private Integer startPeriods;

    /**
     * 结束期数（月）
     */
    private Integer endPeriods;

	/**
	 * 费率/金额
	 */
	private java.math.BigDecimal rateAmount;

    /**
     * 费用收取方式（FIXED_RATIO:按固定比例；FIXED_AMOUNT:按固定金额；）
     */
    private CostChargeModeEnum chargeMode;

    /**
     * 费用收取方式名称
     */
    private String chargeModeName;


    /**
     * 扩展费用参数
     */
    private Map<String, Object> extendParams;
    
    public void setChargeMode(CostChargeModeEnum costChargeModeEnum) {
        this.chargeMode = costChargeModeEnum;
        this.chargeModeName = costChargeModeEnum.getName();
    }

}

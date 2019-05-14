package com.goode.loader.dto;

import com.goode.loader.enums.CostCarryModeEnum;
import com.goode.loader.enums.CostChargeChanceEnum;
import com.goode.loader.enums.PeriodsTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * 费用配置参数<br>
 * <br>
 *
 * @author 谭荣巧
 * @Date 2018/7/13 11:52
 */
@Data
public class CostConfigParamDto {
    /**
     * 费用编码
     */
    private String costCode;

    /**
     * 费用名称
     */
    private String costName;

    /**
     * 费用简称
     */
    private String costShortName;


    /**
     * 费用类型编码，关联费用类型表code
     */
    private String costTypeCode;

    /**
     * 进位方式（TWO_DECIMALS_AFTER_ROUND:保留两位小数，后一位四舍五入；TWO_DECIMALS_AFTER_Carry:保留两位小数，后一位进位；TWO_DECIMALS_AFTER_reject:保留两位小数，后一位直接舍弃）
     */
    private CostCarryModeEnum carryMode;

    /**
     * 费用收取时机（MONTH_BEGIN_CHARGE:按月期初收取、MONTH_END_CHARGE:按月期末收取、SINGLE_USE_CHARGE:一次性收取）
     */
    private CostChargeChanceEnum chargeChance;

	//收取时机名称
	private String chargeChanceName;

    /**
     * 费用计算模型编码
     */
    private String calcModeCode;

    /**
     * 是否设置期限范围（0：否  1：是）
     */
    private Boolean isDeadlineRange;

    /**
     * 资金渠道编码
     */
    private String fundChannelCode;

    /**
     * 期数类型（YEAR:年、MONTH:月、DAY:日）
     */
    private PeriodsTypeEnum periodsType;

    /**
     * 期数
     */
    private Integer periods;
    
    /**
     * 金额
     */
    private Integer amount;
    
    private String calcModeVersion;
    /**
     * 配置扩展参数
     */
    private List<CostConfigExtendParamDto> extendList;
}

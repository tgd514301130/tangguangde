package com.goode.loader.base;

import com.goode.loader.dto.CostAmountDto;
import com.goode.loader.dto.CostConfigParamDto;
import com.goode.loader.enums.CostCarryModeEnum;
import com.goode.loader.enums.CostChargeChanceEnum;
import com.goode.loader.enums.CostChargeModeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 费用计算模型
 *
 * @author 唐光德
 * @Date 2018/05/29 16:16
 */
public abstract class AbstractCalcHandler {

    /**
     * 执行算法的计算
     *
     * @param amount             标的金额
     * @param dto 费用配置参数
     * @return 费用计划，如果设置期限范围，则有多条费用金额，如果不设置期限范围，则只有一条费用金额
     */
    public abstract List<CostAmountDto> execute(int amount, CostConfigParamDto dto);


    /**
     * 默认算法<br>
     *
     * @param amount       标的金额/业务的金额
     * @param rateAmount   费率/年化率/固定费用
     * @param chargeMode   费用收取方式（FIXED_RATIO:按固定比例；FIXED_AMOUNT:按固定金额；）
     * @param carryMode    进位方式（TWO_DECIMALS_AFTER_ROUND:保留两位小数，后一位四舍五入；TWO_DECIMALS_AFTER_Carry:保留两位小数，后一位进位；TWO_DECIMALS_AFTER_reject:保留两位小数，后一位直接舍弃）
     *                     默认：TWO_DECIMALS_AFTER_ROUND：保留两位小数，后一位四舍五入
     * @param chargeChance 费用收取时机（MONTH_BEGIN_CHARGE:按月期初收取、MONTH_END_CHARGE:按月期末收取、SINGLE_USE_CHARGE:一次性收取）
     *                     默认：SINGLE_USE_CHARGE：一次性收取
     * @return java.math.BigDecimal 算出费用
     * @author 谭荣巧
     * @date 2018/8/4 14:24
     */
    protected BigDecimal defualtCalc(BigDecimal amount, BigDecimal rateAmount, CostChargeModeEnum chargeMode, CostCarryModeEnum carryMode, CostChargeChanceEnum chargeChance) {
        BigDecimal decimal100 = new BigDecimal(100);
        BigDecimal amountResult;
        //判断收取方式
        switch (chargeMode) {
            //按固定金额收取
            case FIXED_AMOUNT:
                //默认算法：固定金额
                amountResult = rateAmount;
                break;
            //按固定比例收取
            case FIXED_RATIO:
                //根据收取时机计算费用
                switch (chargeChance) {
                    //按月收，默认算法：标的金额*年化率/12
                    case MONTH_BEGIN_CHARGE:
                    case MONTH_END_CHARGE:
                        amountResult = amount.multiply((rateAmount.divide(decimal100, 10, RoundingMode.HALF_DOWN)));
                        break;
                    //一次性收取，默认算法：标的金额*费率
                    case SINGLE_USE_CHARGE:
                    default:
                        //默认按一次性收取计算费用
                        amountResult = amount.multiply((rateAmount.divide(decimal100, 10, RoundingMode.HALF_DOWN)));
                        break;
                }
                //费用金额进位
                amountResult = carry(amountResult, carryMode);
                break;
            default:
                throw new RuntimeException("计算失败，没有合适的收取方式，当前收取方式为：" + chargeMode);
        }
        return amountResult;
    }

    /**
     * 进位<br>
     *
     * @param carryMode 进位方式（TWO_DECIMALS_AFTER_ROUND:保留两位小数，后一位四舍五入；TWO_DECIMALS_AFTER_Carry:保留两位小数，后一位进位；TWO_DECIMALS_AFTER_reject:保留两位小数，后一位直接舍弃）
     *                  默认：TWO_DECIMALS_AFTER_ROUND
     * @return
     * @author 谭荣巧
     * @date 2018/8/4 14:52
     */
    protected BigDecimal carry(BigDecimal amount, CostCarryModeEnum carryMode) {
        //判断进位方式
        switch (carryMode) {
            //保留两位小数，后一位进位
            case TWO_DECIMALS_AFTER_CARRY:
                return amount.setScale(2, BigDecimal.ROUND_UP);
            //保留两位小数，后一位直接舍弃
            case TWO_DECIMALS_AFTER_REJECT:
                return amount.setScale(2, BigDecimal.ROUND_DOWN);
            //保留两位小数，后一位四舍五入
            case TWO_DECIMALS_AFTER_ROUND:
            default:
                return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

}

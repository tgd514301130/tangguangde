package com.goode.loader.base;


import com.goode.loader.dto.CostAmountDto;
import com.goode.loader.dto.CostConfigExtendParamDto;
import com.goode.loader.dto.CostConfigParamDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算模型样例代码（前端使用）<br>
 *
 * @Date 2018/7/16 20:49
 */
public class CostCalcHandlerImpl extends AbstractCalcHandler {

    @Override
    public List<CostAmountDto> execute(int amount, CostConfigParamDto costConfigParamDto) {
        List<CostAmountDto> list = new ArrayList<>();
        BigDecimal amountDecimal = new BigDecimal(amount);
        CostAmountDto costAmountDto;
        //判断是否期收
        if (costConfigParamDto.getIsDeadlineRange()) {
            List<CostConfigExtendParamDto> extendList = costConfigParamDto.getExtendList();
            if (extendList != null && extendList.size() > 0) {
                for (CostConfigExtendParamDto costConfigExtendParamDto : extendList) {
                    costAmountDto = new CostAmountDto();
                    costAmountDto.setValue(costConfigExtendParamDto);
                    //使用默认算法计算费用
                    costAmountDto.setAmount(defualtCalc(amountDecimal, costConfigExtendParamDto.getRateAmount(), costConfigExtendParamDto.getChargeMode(), costConfigParamDto.getCarryMode(), costConfigParamDto.getChargeChance()));
                    list.add(costAmountDto);
                }
            }
        } else {
            CostConfigExtendParamDto extendDto = new CostConfigExtendParamDto();
            if (costConfigParamDto.getExtendList() != null && costConfigParamDto.getExtendList().size() > 0) {
                extendDto = costConfigParamDto.getExtendList().get(0);
            }
            costAmountDto = new CostAmountDto();
            costAmountDto.setValue(extendDto);
            //使用默认算法计算费用
            costAmountDto.setAmount(defualtCalc(amountDecimal, extendDto.getRateAmount(), extendDto.getChargeMode(), costConfigParamDto.getCarryMode(), costConfigParamDto.getChargeChance()));
            list.add(costAmountDto);
        }
        return list;
    }
}

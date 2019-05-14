package com.goode.loader;

import com.goode.loader.base.AbstractCalcHandler;
import com.goode.loader.dto.CostAmountDto;
import com.goode.loader.dto.CostConfigParamDto;
import com.goode.loader.groovy.GroovyCache;
import com.goode.loader.groovy.GroovyFactory;
import groovy.json.JsonOutput;

import java.util.List;

/**
 * @author HX0013522
 * @date 2019-05-14 18:58:58
 */
public class TestLoader {


    private static String dbCode = "" +
            "package com.goode.loader.base;\n" +
            "\n" +
            "\n" +
            "import com.goode.loader.dto.CostAmountDto;\n" +
            "import com.goode.loader.dto.CostConfigExtendParamDto;\n" +
            "import com.goode.loader.dto.CostConfigParamDto;\n" +
            "\n" +
            "import java.math.BigDecimal;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.List;\n" +
            "\n" +
            "/**\n" +
            " * 计算模型样例代码（前端使用）<br>\n" +
            " *\n" +
            " * @Date 2018/7/16 20:49\n" +
            " */\n" +
            "public class CostCalcHandlerImpl extends AbstractCalcHandler {\n" +
            "\n" +
            "    @Override\n" +
            "    public List<CostAmountDto> execute(int amount, CostConfigParamDto costConfigParamDto) {\n" +
            "        List<CostAmountDto> list = new ArrayList<>();\n" +
            "        BigDecimal amountDecimal = new BigDecimal(amount);\n" +
            "        CostAmountDto costAmountDto;\n" +
            "        //判断是否期收\n" +
            "        if (costConfigParamDto.getIsDeadlineRange()) {\n" +
            "            List<CostConfigExtendParamDto> extendList = costConfigParamDto.getExtendList();\n" +
            "            if (extendList != null && extendList.size() > 0) {\n" +
            "                for (CostConfigExtendParamDto costConfigExtendParamDto : extendList) {\n" +
            "                    costAmountDto = new CostAmountDto();\n" +
            "                    costAmountDto.setValue(costConfigExtendParamDto);\n" +
            "                    //使用默认算法计算费用\n" +
            "                    costAmountDto.setAmount(defualtCalc(amountDecimal, costConfigExtendParamDto.getRateAmount(), costConfigExtendParamDto.getChargeMode(), costConfigParamDto.getCarryMode(), costConfigParamDto.getChargeChance()));\n" +
            "                    list.add(costAmountDto);\n" +
            "                }\n" +
            "            }\n" +
            "        } else {\n" +
            "            CostConfigExtendParamDto extendDto = new CostConfigExtendParamDto();\n" +
            "            if (costConfigParamDto.getExtendList() != null && costConfigParamDto.getExtendList().size() > 0) {\n" +
            "                extendDto = costConfigParamDto.getExtendList().get(0);\n" +
            "            }\n" +
            "            costAmountDto = new CostAmountDto();\n" +
            "            costAmountDto.setValue(extendDto);\n" +
            "            //使用默认算法计算费用\n" +
            "            costAmountDto.setAmount(defualtCalc(amountDecimal, extendDto.getRateAmount(), extendDto.getChargeMode(), costConfigParamDto.getCarryMode(), costConfigParamDto.getChargeChance()));\n" +
            "            list.add(costAmountDto);\n" +
            "        }\n" +
            "        return list;\n" +
            "    }\n" +
            "}\n";


    public static void main(String[] args) {
        //将数据库存储的代码拿出来编译好，放到JVM缓存
        try {
            GroovyCache.costCalcModeMap.put("id-key", GroovyFactory.getInstance().loadNewCalcHandlerInstance(dbCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从JVM缓存取编译好的代码执行
        AbstractCalcHandler handler = GroovyCache.costCalcModeMap.get("id-key");
        List<CostAmountDto> ret = handler.execute(10000, new CostConfigParamDto());
        //得到算法执行的结果
        System.out.println(JsonOutput.toJson(ret));
    }
}

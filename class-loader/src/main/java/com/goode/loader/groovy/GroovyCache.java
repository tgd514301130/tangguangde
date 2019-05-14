package com.goode.loader.groovy;

import com.goode.loader.base.AbstractCalcHandler;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author HX0013522
 * @date 2019-05-14 19:09:09
 */
public class GroovyCache {

    /**
     * 费用计算模型算法map<br/>
     * key:费用计算模型编码+"_"+费用计算模型版本号。value为数据库配置的算法类
     */
    public static Map<String, AbstractCalcHandler> costCalcModeMap = Maps.newHashMap();

}

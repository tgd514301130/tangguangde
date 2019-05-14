package com.goode.loader.base;

import com.goode.loader.util.StringUtil;

/**
 * 常量
 *
 * @Date: 2018/5/23 14:59
 */
public class Constants {


    /**
     * 本地机器名称
     */
    public static final String HOST_NAME = StringUtil.getHostName();

    /**
     * pmc rabbitmq fanout 队列名称
     */
    public static final String FANOUT_QUEUE = "pmc_fanout_queue";

    /**
     * pmc rabbitmq fanout exchange名称
     */
    public static final String FANOUT_EXCHANGE = "pmc_fanout_exchange";

    /**
     * 动态消息队列名称
     */
    public static final String DYN_QUEUE_NAME = FANOUT_QUEUE + HOST_NAME;

}

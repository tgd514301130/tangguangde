package com.goode.loader.message;


import java.io.Serializable;
import java.util.Date;

/**
 * Rabbit消息体
 *
 * @author 唐光德
 * @Date 2018/06/22 17:14
 */
public class RabbitMessage implements Serializable {

    /**
     * 业务类型：不同业务类型，消费方处理机制不一样
     */
    private Integer businessType;

    /**
     * 消息体
     */
    private Object data;

    /**
     * 消息发送时间
     */
    private Date sendTime;


    public RabbitMessage() {
    }

    public RabbitMessage(Integer businessType, Object data) {
        this.businessType = businessType;
        this.data = data;
        this.sendTime = new Date();
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}

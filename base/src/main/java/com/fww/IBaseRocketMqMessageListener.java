package com.fww;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/04/29 20:23
 */
public interface IBaseRocketMqMessageListener {
    boolean onMessage(List<MessageExt> var1, ConsumeConcurrentlyContext var2);
}

package com.fww;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/04/29 20:22
 */
public abstract class BaseRocketMqMessageListener implements IBaseRocketMqMessageListener{
    public BaseRocketMqMessageListener() {
    }

    @Override
    public boolean onMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
        return this.onReceiveMessage(messages, context);
    }

    public abstract boolean onReceiveMessage(List<MessageExt> var1, ConsumeConcurrentlyContext var2);
}

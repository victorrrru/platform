package com.fww.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import java.nio.charset.Charset;
import java.util.List;

public class RocketMqLoanConsumerListener extends BaseRocketMqMessageListener{

	@Override
	public boolean onReceiveMessage(List<MessageExt> lsgMsg, ConsumeConcurrentlyContext context) {
		for(MessageExt msg : lsgMsg){
			System.out.println("消费---------------->topic=" + msg.getTopic()+", tag="+msg.getTags()+", keys="+msg.getKeys()+", body="+ StringUtils.toEncodedString(msg.getBody(), Charset.defaultCharset()));
		}
		return true;
	}

}

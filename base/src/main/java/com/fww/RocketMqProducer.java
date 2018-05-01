package com.fww;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/04/29 19:10
 */
public class RocketMqProducer {
    private static Logger logger = Logger.getLogger(RocketMqProducer.class);
    private static String namesrvAddr;
    private static String producerGroup;
    private static DefaultMQProducer producer;
    private static RocketMqProducer rocketMqProducer;

    public RocketMqProducer() {
    }

    @PostConstruct
    public static void init() {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        rocketMqProducer = new RocketMqProducer();
        rocketMqProducer.setNamesrvAddr(namesrvAddr);
        rocketMqProducer.setProducerGroup(producerGroup);
        RocketMqProducer var10000 = rocketMqProducer;
        setProducer(producer);

        try {
            producer.start();
        } catch (Exception var1) {
            logger.error("初始化消息生产者对象异常！", var1);
        }

    }

    @PreDestroy
    public void destroy() {
        producer.shutdown();
    }

    public static RocketMqProducer getInstance() {
        if(rocketMqProducer == null) {
            init();
            return rocketMqProducer;
        } else {
            return rocketMqProducer;
        }
    }

    public boolean send(List<Map<String, String>> lstMsg) {
        List<Message> lst = new ArrayList();
        Iterator var3 = lstMsg.iterator();

        while(var3.hasNext()) {
            Map<String, String> map = (Map)var3.next();
            Message msg = new Message((String)map.get("topic"), (String)map.get("tag"), (String)map.get("jsonKeys"), ((String)map.get("body")).getBytes());
            lst.add(msg);
        }

        try {
            SendResult result = producer.send(lst);
            logger.info("rocketMq消息发送结果：" + JSONObject.toJSONString(result));
            if("SEND_OK".equalsIgnoreCase(result.getSendStatus().toString())) {
                return true;
            }
        } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException var6) {
            logger.error("rocketMq发送异常！", var6);
        }

        return false;
    }

    public boolean send(String topic, String tag, String jsonKeys, String body) {
        if(!StringUtils.isBlank(topic) && !StringUtils.isBlank(tag) && !StringUtils.isBlank(jsonKeys) && !StringUtils.isBlank(body)) {
            try {
                SendResult result = producer.send(new Message(topic, tag, jsonKeys, body.getBytes()));
                logger.info("rocketMq消息发送结果：" + JSONObject.toJSONString(result));
                if("SEND_OK".equalsIgnoreCase(result.getSendStatus().toString())) {
                    return true;
                }
            } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException var6) {
                logger.error("rocketMq发送异常！", var6);
            }

            return false;
        } else {
            logger.error("topic/tag/key/body不能为空,topic=" + topic + ",tag=" + tag + ", key=" + jsonKeys + ", body=" + body);
            return false;
        }
    }

    public boolean send(String topic, String tag, String body) {
        if(!StringUtils.isBlank(topic) && !StringUtils.isBlank(tag) && !StringUtils.isBlank(body)) {
            try {
                SendResult result = producer.send(new Message(topic, tag, body.getBytes()));
                logger.info("rocketMq消息发送结果：" + JSONObject.toJSONString(result));
                if("SEND_OK".equalsIgnoreCase(result.getSendStatus().toString())) {
                    return true;
                }
            } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException var5) {
                logger.error("rocketMq发送异常！", var5);
            }

            return false;
        } else {
            logger.error("topic/tag/body不能为空,topic=" + topic + ",tag=" + tag + ", body=" + body);
            return false;
        }
    }

    public boolean send(String topic, String body) {
        if(!StringUtils.isBlank(topic) && !StringUtils.isBlank(body)) {
            try {
                SendResult result = producer.send(new Message(topic, body.getBytes()));
                logger.info("rocketMq消息发送结果：" + JSONObject.toJSONString(result));
                if("SEND_OK".equalsIgnoreCase(result.getSendStatus().toString())) {
                    return true;
                }
            } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException var4) {
                logger.error("rocketMq发送异常！", var4);
            }

            return false;
        } else {
            logger.error("topic/body不能为空,topic=" + topic + ", body=" + body);
            return false;
        }
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        producerGroup = producerGroup;
    }

    public static void setProducer(DefaultMQProducer producer) {
        producer = producer;
    }
}

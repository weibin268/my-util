package com.zhuang.util.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class KafkaUtils {

    private static KafkaUtils _this;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostConstruct
    private void init() {
        _this = this;
    }

    public static ListenableFuture<SendResult<String, Object>> send(String topic, Object data) {
        // 不指定key，集群多节点下会有问题
        String key = UUID.randomUUID().toString();
        return _this.kafkaTemplate.send(topic, key, data);
    }

}

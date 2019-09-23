package com.huangniuniu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuangniuniuSmsApplication.class)
public class HuangniuniuTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSendSms(){
        Map<String,String> msg = new HashMap<>();
        msg.put("phone","13353019284");
        msg.put("code","123456");
        amqpTemplate.convertAndSend("huangniuniu.sms.exchange","sms.verify.code",msg);
    }


}

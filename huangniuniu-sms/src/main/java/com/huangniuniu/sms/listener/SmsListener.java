package com.huangniuniu.sms.listener;

import com.huangniuniu.sms.utils.SmsUtil;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "huangniuniu.sms.queue", durable = "true"),
            exchange = @Exchange(value = "huangniuniu.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void sendSms(Map<String,String> msg) throws HttpException, IOException {
        if(CollectionUtils.isEmpty(msg)){
            return ;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        System.out.println("手机号："+phone);
        if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)){
            this.smsUtil.sendSsms(phone,code);
        }
    }
}

package com.huangniuniu.sms.utils;

import com.huangniuniu.sms.config.SmsProperties;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtil {

    @Autowired
    private SmsProperties properties;

    public void sendSsms(String phone,String code) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        //拼接验证信息
        String smsText=properties.getPreCode()+code+properties.getAfterCode();

        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data ={
                new NameValuePair("Uid", properties.getUid()),
                new NameValuePair("Key", properties.getKey()),
                new NameValuePair("smsMob",phone),
                new NameValuePair("smsText",smsText)
        };
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:"+statusCode);

        for(Header h : headers){
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result);

        post.releaseConnection();

    }
}

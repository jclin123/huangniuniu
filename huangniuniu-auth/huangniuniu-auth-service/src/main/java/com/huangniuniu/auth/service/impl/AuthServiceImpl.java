package com.huangniuniu.auth.service.impl;

import com.huangniuniu.auth.client.UserClient;
import com.huangniuniu.auth.config.JwtProperties;
import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.service.AuthService;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String accountPassword(String account, String password) {

        //远程调用微服务，根据用户名和密码查询
        User user = userClient.loginByAccountAndPsw(account, password);
        if(user == null){
            return null;
        }

        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getNickname());
            return JwtUtils.generateToken(userInfo,jwtProperties.getPrivateKey(),jwtProperties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

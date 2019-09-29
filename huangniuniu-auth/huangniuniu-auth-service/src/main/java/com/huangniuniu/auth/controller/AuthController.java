package com.huangniuniu.auth.controller;

import com.huangniuniu.auth.config.JwtProperties;
import com.huangniuniu.auth.service.AuthService;
import com.huangniuniu.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 根据账号和密码授权
     * @param account
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accountPassword")
    public ResponseEntity<Void> accountPassword(@RequestParam("account")String account,
                                         @RequestParam("password")String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        String token = authService.accountPassword(account,password);

        //授权未通过
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);
        return ResponseEntity.ok(null);
    }

}

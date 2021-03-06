package com.huangniuniu.auth.controller;

import com.huangniuniu.auth.config.JwtProperties;
import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.service.AuthService;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 根据账号和密码授权登录
     * @param account
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accountpassword")
    public ResponseEntity<Void> accountPassword(@RequestParam(value = "account")String account,
                                         @RequestParam(value = "password")String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        Map<String, String> map = authService.accountPassword(account, password);
        String type = map.get("type");
        String token = map.get("token");
        return setCookieBytoken(type,token, request, response);

    }

    /**
     * 根据手机号和验证码授权登录
     * @param phonenumber
     * @param code
     * @param request
     * @param response
     * @return
     */
    @PostMapping("phonenumbercode")
    public ResponseEntity<Void> PhoneAndCode(@RequestParam("phonenumber")String phonenumber,
                                             @RequestParam("code")String code,
                                             HttpServletRequest request,
                                             HttpServletResponse response){
        Map<String, String> map = authService.PhoneAndCode(phonenumber, code);
        String type = map.get("type");
        String token = map.get("token");
        return setCookieBytoken(type,token, request, response);
    }

    private ResponseEntity<Void> setCookieBytoken(String type,String token,HttpServletRequest request,
                                                  HttpServletResponse response){
        //授权未通过
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if("0".equals(type)) {//管理页面
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire() * 60);
        }else if("1".equals(type)){//普通用户页面
            CookieUtils.setCookie(request, response, jwtProperties.getCookieUserName(), token, jwtProperties.getExpire() * 60);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 验证用户信息，管理页面
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(
            @CookieValue("Huangniuniu_TOKEN")String token,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        try {
            // 公钥解析jwt，从token中解析token信息
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //刷新jwt中的有效时间,即重新生成token
            token = JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());

            //刷新cookie中的有效时间，即更新生成cookie
            CookieUtils.setCookie(request,response,this.jwtProperties.getCookieName(),token,this.jwtProperties.getExpire()*60);

            // 解析成功返回用户信息
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 出现异常则，响应500
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 验证用户信息，普通用户页面
     * @param token
     * @return
     */
    @GetMapping("verifyuser")
    public ResponseEntity<UserInfo> verifyUser2(
            @CookieValue("Huangniuniu_USERTOKEN")String token,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        try {
            // 公钥解析jwt，从token中解析token信息
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if(user == null && user.getRoleType() != 1){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //刷新jwt中的有效时间,即重新生成token
            token = JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());

            //刷新cookie中的有效时间，即更新生成cookie
            CookieUtils.setCookie(request,response,this.jwtProperties.getCookieUserName(),token,this.jwtProperties.getExpire()*60);

            // 解析成功返回用户信息
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 出现异常则，响应500
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 退出登录，管理页面
     * @param token
     * @param request
     * @param response
     * @return
     */
    @GetMapping("exit")
    public ResponseEntity<Void> exitLogin(@CookieValue("Huangniuniu_TOKEN")String token,
                                          HttpServletRequest request,
                                          HttpServletResponse response){
        try {
            // 公钥解析jwt，从token中解析token信息
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //删除token
            CookieUtils.deleteCookie(request,response,this.jwtProperties.getCookieName());

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 退出登录，普通用户页面
     * @param token
     * @param request
     * @param response
     * @return
     */
    @GetMapping("exituser")
    public ResponseEntity<Void> exituserLogin(@CookieValue("Huangniuniu_USERTOKEN")String token,
                                          HttpServletRequest request,
                                          HttpServletResponse response){
        try {
            // 公钥解析jwt，从token中解析token信息
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if(user == null && user.getRoleType() != 1){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //删除token
            CookieUtils.deleteCookie(request,response,this.jwtProperties.getCookieUserName());

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(null);
    }


}

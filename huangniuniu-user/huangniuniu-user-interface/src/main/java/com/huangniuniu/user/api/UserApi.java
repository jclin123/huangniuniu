package com.huangniuniu.user.api;

import com.huangniuniu.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    /**
     * 账号(管理员为账号，用户为手机号)和密码登录
     * @param account
     * @param password
     * @return
     */
    @GetMapping("accountPassword")
    public User loginByAccountAndPsw(@RequestParam("account")String account,
                                     @RequestParam("password")String password);

    /**
     * 手机号和验证码登录
     * @param phonenumber
     * @param code
     * @return
     */
    @GetMapping("phonenumberCode")
    public User loginByPhoneAndCode(@RequestParam("phonenumber")String phonenumber,
                                    @RequestParam("code")String code);
}

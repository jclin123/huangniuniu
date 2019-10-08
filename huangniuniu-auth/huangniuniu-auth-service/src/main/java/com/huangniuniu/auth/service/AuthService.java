package com.huangniuniu.auth.service;

public interface AuthService {

    /**
     * 根据账号和密码授权
     * @param account
     * @param password
     * @return
     */
    String accountPassword(String account, String password);

    /**
     * 根据手机号和验证码授权登录
     * @param phonenumber
     * @param code
     * @return
     */
    String PhoneAndCode(String phonenumber, String code);
}

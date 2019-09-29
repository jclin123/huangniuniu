package com.huangniuniu.auth.service;

public interface AuthService {

    /**
     * 根据账号和密码授权
     * @param account
     * @param password
     * @return
     */
    String accountPassword(String account, String password);
}

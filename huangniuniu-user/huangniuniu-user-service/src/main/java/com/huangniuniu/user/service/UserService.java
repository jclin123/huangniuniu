package com.huangniuniu.user.service;

import com.huangniuniu.user.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 判断手机号是否存在
     * @param phonenumber
     * @return
     */
    Boolean isexist(String phonenumber);

    /**
     * 发送短信验证码
     */
    void sendSmsCode(String phoneNumber);

    /**
     * 根据用户信息和验证码注册用户
     * @param user
     * @param code
     */
    Boolean insertUser(User user, String code);

    /**
     * 根据手机号（账号）＋密码登录，管理员和普通用户登录共用
     * @param account
     * @param password
     */
    User loginByAccountAndPsw(String account,String password);

    /**
     * 根据手机号和验证码登录
     * @param phonenum
     * @param code
     */
    User loginByPhoneAndCode(String phonenum,String code);

    /**
     * 根据用户id解冻（激活）用户
     * @param id
     */
    public void setIsDisable(Long id);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 条件查询用户信息
     * @param user
     * @return
     */
    List<User> getUserByCondition(User user);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    User getUserByUserId(Long id);
}

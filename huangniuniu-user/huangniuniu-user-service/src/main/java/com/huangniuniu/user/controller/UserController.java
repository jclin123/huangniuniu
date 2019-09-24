package com.huangniuniu.user.controller;

import com.huangniuniu.user.pojo.User;
import com.huangniuniu.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 判断要注册的手机号是否存在
     * @param phonenumber
     * @return
     */
    @GetMapping("phonenumber")
    public ResponseEntity<Boolean> isexist(@RequestParam("phonenumber")String phonenumber){
        if(StringUtils.isBlank(phonenumber)){
            return ResponseEntity.badRequest().build();
        }
        Boolean bool = userService.isexist(phonenumber);
        return ResponseEntity.ok(bool);//查询成功，响应200和数据
    }

    /**
     * 根据手机号发送验证码
     * @param phonenumber
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendSmsCode(@RequestParam("phonenumber")String phonenumber){
        userService.sendSmsCode(phonenumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();//创建成功，响应201
    }

    /**
     * 用户注册
     * @param code
     * @param user
     * @return
     */
    @PostMapping("registry")
    public ResponseEntity<Boolean> insertUser(@RequestParam("code")String code, @Valid User user){
        Boolean bool = userService.insertUser(user, code);
        if(!bool){
            return ResponseEntity.badRequest().build();//参数问题，响应400
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bool);
    }

    /**
     * 账号(管理员为账号，用户为手机号)和密码登录
     * @param account
     * @param password
     * @return
     */
    @GetMapping("accountPassword")
    public ResponseEntity<User> loginByAccountAndPsw(@RequestParam("account")String account,
                                                     @RequestParam("password")String password){
        User user = userService.loginByAccountAndPsw(account, password);
        //账号或者密码错误
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 手机号和验证码登录
     * @param phonenumber
     * @param code
     * @return
     */
    @GetMapping("phonenumberCode")
    public ResponseEntity<User> loginByPhoneAndCode(@RequestParam("phonenumber")String phonenumber,
                                                    @RequestParam("code")String code){
        User user = userService.loginByPhoneAndCode(phonenumber, code);
        //手机号或者验证码错误
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping("queryAll")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        if(allUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allUser);
    }

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    @GetMapping("conditionQuery")
    public ResponseEntity<List<User>> getUserByCondition(User user){
        List<User> list = userService.getUserByCondition(user);
        if(list == null){
            return ResponseEntity.notFound().build();//查询不到，响应404
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据用户的id，修改状态，正常或者冻结
     * @param id
     * @return
     */
    @PutMapping("isDisable/{id}")
    public ResponseEntity<Void> setisDisable(@PathVariable("id")Long id){
            userService.setIsDisable(id);
            return ResponseEntity.noContent().build();//更新和删除成功，响应204
    }

}

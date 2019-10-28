package com.huangniuniu.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.common.utils.NumberUtils;
import com.huangniuniu.user.mapper.UserMapper;
import com.huangniuniu.user.pojo.User;
import com.huangniuniu.user.service.UserService;
import com.huangniuniu.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:";

    @Override
    public Boolean isexist(String phonenumber) {
        User user = new User();
        user.setAccount(phonenumber);
        int count = userMapper.selectCount(user);
        if(count == 1){
            return true;
        }
        return false;
    }

    @Override
    public void sendSmsCode(String phoneNumber) {
        //手机号非空校验
        if(StringUtils.isBlank(phoneNumber)){
            return ;
        }

        //随机产生6位数字的验证码
        String code = NumberUtils.generateCode(6);

        //发送给消息队列
        Map<String,String> msg = new HashMap<>();
        msg.put("phone",phoneNumber);
        msg.put("code",code);
        amqpTemplate.convertAndSend("huangniuniu.sms.exchange","sms.verify.code",msg);

        //保存在redis缓存5分钟
        redisTemplate.opsForValue().set(KEY_PREFIX+phoneNumber,code,5, TimeUnit.MINUTES);
    }

    @Override
    @Transactional
    public Boolean insertUser(User user, String code) {

        //判断数据库中手机号是否存在
        Boolean isexist = isexist(user.getAccount());
        if(isexist){//存在
            return false;
        }

        //校验验证码的合法性
        String redis_code = redisTemplate.opsForValue().get(KEY_PREFIX + user.getAccount());
        if(!StringUtils.equals(redis_code,code)){
            return false;
        }

        //生成盐
        String salt = CodecUtils.generateSalt();
        //给密码加盐加密
        String salt_password = CodecUtils.md5Hex(user.getUserPassword(), salt);
        //设置用户信息
        user.setId(null);
        user.setIsDisable(1);//注册为正常状态
        user.setRegistrationDate(new Date());
        user.setRoleType(1);
        user.setUserPassword(salt_password);
        user.setSalt(salt);

        if(userMapper.insertSelective(user)==1){
            //删除缓存中的记录
            redisTemplate.delete(KEY_PREFIX+user.getAccount());
            return true;
        }
        return false;

    }

    @Override
    public User loginByAccountAndPsw(String account, String password) {
        //根据用户账号(手机号)查询用户信息
        User user = new User();
        user.setAccount(account);
        User user1 = userMapper.selectOne(user);
        //判断该账号的用户是否存在
        /*if(user1 == null || user1.getRoleType() != 0){
            return null;
        }*/
        if(user1 == null){
            return null;
        }

        //获得该账户(手机号)下密码和盐
        String user1Salt = user1.getSalt();
        String user1UserPassword = user1.getUserPassword();

        //对查询到的用户的密码与页面密码（进行加盐加密后的）比较
        String userPassword = CodecUtils.md5Hex(password, user1Salt);
        if(StringUtils.equals(user1UserPassword,userPassword)){
            return user1;
        }
        return null;
    }

    @Override
    public User loginByPhoneAndCode(String phonenum, String code) {
        //判断手机号是否存在
        User user = new User();
        user.setAccount(phonenum);
        User user1 = userMapper.selectOne(user);
        //判断用户是否存在
        if(user1 == null){
            return null;
        }
        //存在，判断验证码的合法性
        String redis_code = redisTemplate.opsForValue().get(KEY_PREFIX + phonenum);
        if(StringUtils.equals(code,redis_code)){
            //删除redis记录
            //redisTemplate.delete(KEY_PREFIX+phonenum);
            return user1;
        }
        return null;

    }

    @Transactional
    public void setIsDisable(Long id) {
        //判断用户的状态
        User user = userMapper.selectByPrimaryKey(id);
        //根据用户状态修改为相反的状态
        if(user.getIsDisable() == 0){
            updateIsDisable(id,1);
        }else{
            updateIsDisable(id,0);
        }
    }

    @Override
    public List<User> getAllUser() {
        //查询用户信息，不包括管理员的信息
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleType",1);

        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public PageResult<User> getUserByCondition(User user,Integer pageNumber,Integer pageSize) {

        PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(user.getNickname())){
            criteria.andLike("nickname","%"+user.getNickname()+"%");
        }
        if(user.getIsDisable() != null){
            criteria.andLike("isDisable","%"+user.getIsDisable()+"%");
        }
        criteria.andEqualTo("roleType",1);//用户信息
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());

    }

    @Override
    public User getUserByUserId(Long id) {
        return  userMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<User> queryAllByPage(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleType",1);//用户信息
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    private void updateIsDisable(Long id,Integer isd){
        User user = new User();
        user.setId(id);
        user.setIsDisable(isd);
        userMapper.updateByPrimaryKeySelective(user);
    }

}

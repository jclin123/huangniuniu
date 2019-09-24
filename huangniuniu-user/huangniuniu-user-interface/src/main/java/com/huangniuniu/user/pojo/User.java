package com.huangniuniu.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@Table(name = "user")
public class User {
    //这里对password和salt添加了注解@JsonIgnore，这样在json序列化时，就不会把password和salt返回

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[356789]\\d{9}$",message = "请输入正确的手机号")
    private String account;

    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度在6-16位")
    private String userPassword;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private Integer roleType;

    private Date registrationDate;

    private Integer isDisable;

    @JsonIgnore
    private String salt;//加盐

}

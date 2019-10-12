package com.huangniuniu.order.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//@Getter
//@Setter
@Data
@Table(name = "userorder")
@Accessors(chain = true)
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer orderNum;
    private Date orderTime;
    private Long userid;
    private Long skeduleid;
    private String nickname;
}

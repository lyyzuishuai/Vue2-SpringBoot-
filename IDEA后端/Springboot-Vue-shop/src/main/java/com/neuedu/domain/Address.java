package com.neuedu.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("s_address")
@ApiModel(value = "Address", description = "用户地址信息")
public class Address {
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;
    private String username;
    private String nickname;
    private String addressName;
    private String usersPhone;
    private String usersName;
    private String postcode;
    private Integer usersId;
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer isDelete;
    @TableField(exist = false)
    private Users users;

}

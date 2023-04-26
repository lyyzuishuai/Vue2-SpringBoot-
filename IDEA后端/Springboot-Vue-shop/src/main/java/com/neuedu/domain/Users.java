package com.neuedu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("s_users")
@ApiModel(value = "Users", description = "用户实体")
public class Users {
    @TableId(value = "users_id", type = IdType.AUTO)
    private Integer usersId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Integer sex;
    private String description;
    private String remark;
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer isDelete;

}

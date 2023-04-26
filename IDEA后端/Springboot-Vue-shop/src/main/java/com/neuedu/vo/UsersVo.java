package com.neuedu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UsersVo", description = "接收用户页面的传值")
public class UsersVo {
    @ApiModelProperty(value = "//用户名")
    private String  username;
    @ApiModelProperty(value = "//真实姓名")
    private String nickname;
    @ApiModelProperty(value = "//开始时间")
    private String startTime;
    @ApiModelProperty(value = "//结束时间")
    private String endTime;
}

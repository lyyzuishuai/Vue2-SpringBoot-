package com.neuedu.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("s_classify")
@ApiModel(value = "Classify", description = "商品分类实体")
public class Classify {
    @ApiModelProperty(value = "//商品分类主键")
    @TableId(value = "classify_id", type = IdType.AUTO)
    private Integer classifyId;
    @ApiModelProperty(value = "//商品分类名称")
    private String classifyName;
    @ApiModelProperty(value = "//商品分类描述")
    private String description;
    @ApiModelProperty(value = "//商品分类创建时间")
    private Date createTime;
    @ApiModelProperty(value = "//商品分类修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "//商品分类是否删除 0表示不删除 1表示删除")
    @TableLogic
    private Integer isDelete;
}

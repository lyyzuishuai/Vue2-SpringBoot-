package com.neuedu.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("s_goods")
@ApiModel(value = "Goods", description = "商品实体")
public class Goods {
    @ApiModelProperty(value = "//商品主键")
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;
    @ApiModelProperty(value = "//商品名称")
    private String goodsName;
    @ApiModelProperty(value = "//商品规格")
    private String specification;
    @ApiModelProperty(value = "//商品描述")
    private String description;
    @ApiModelProperty(value = "//商品详情")
    private String particulars;
    @ApiModelProperty(value = "//商品价格")
    private BigDecimal price;
    @ApiModelProperty(value = "//分类Id")
    private Integer classifyId;
    @ApiModelProperty(value = "//商品备注")
    private String remark;
    @ApiModelProperty(value = "//创建时间")
    private Date createTime;
    @ApiModelProperty(value = "//修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "//是否删除")
    @TableLogic
    private Integer isDelete;
    @TableField(exist = false)
    private Classify classify;

}

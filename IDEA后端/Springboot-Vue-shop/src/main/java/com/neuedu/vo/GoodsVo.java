package com.neuedu.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "GoodsVo", description = "商品实体Vo")
public class GoodsVo {
  @ApiModelProperty(value = "//商品ID")
    private Integer goodsId;
    @ApiModelProperty(value = "//商品名称")
    private String  goodsName;
    @ApiModelProperty(value = "//区间开始商品价格")
    private BigDecimal startPrice;
    @ApiModelProperty(value = "//区间结束商品价格")
    private BigDecimal endPrice;
    @ApiModelProperty(value = "//开始时间")
    private String startTime;
    @ApiModelProperty(value = "//结束时间")
    private String endTime;
}

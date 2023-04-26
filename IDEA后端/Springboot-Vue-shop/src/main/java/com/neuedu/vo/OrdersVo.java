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
@ApiModel(value = "OrdersVo", description = "接收页面订单查询信息")
public class OrdersVo {
    @ApiModelProperty(value = "//订单Id")
    private Integer ordersId;
    @ApiModelProperty(value = "//订单编号")
    private String  ordersNumber;
    @ApiModelProperty(value = "//订单名称")
    private String goodsName;
    @ApiModelProperty(value = "//区间开始商品价格")
    private BigDecimal startPrice;
    @ApiModelProperty(value = "//区间结束商品价格")
    private BigDecimal endPrice;
    @ApiModelProperty(value = "//开始时间")
    private String startTime;
    @ApiModelProperty(value = "//收货人姓名")
    private String usersName;
    @ApiModelProperty(value = "//收货人电话")
    private String usersPhone;
    @ApiModelProperty(value = "//结束时间")
    private String endTime;
}

package com.neuedu.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("s_orders")
@ApiModel(value = "Orders", description = "订单信息")
public class Orders {
    @TableId(value = "orders_id", type = IdType.AUTO)
    private Integer ordersId;
    private String ordersNumber;
    private String goodsName;
    private Integer goodsNumber;
    private BigDecimal ordersPrice;
    private String specification;
    private String description;
    private String particulars;
    private Integer usersId;
    private Integer classifyId;
    private Integer status;
    private String usersName;
    private String usersPhone;
    private String addressName;
    private String postcode;
    private String remark;
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer isDelete;


}

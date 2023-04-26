package com.neuedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Orders;
import com.neuedu.vo.OrdersVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    IPage pageList(@Param("ordersVo")Page<Orders> page, OrdersVo ordersVo);
    Integer getMaxId();

}

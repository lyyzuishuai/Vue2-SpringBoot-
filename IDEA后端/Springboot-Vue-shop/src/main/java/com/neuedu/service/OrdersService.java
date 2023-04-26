package com.neuedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.domain.Orders;
import com.neuedu.vo.OrdersVo;


public interface OrdersService extends IService<Orders> {
    IPage pageList(Page<Orders> page, OrdersVo ordersVo);

    Integer getMaxId();


}

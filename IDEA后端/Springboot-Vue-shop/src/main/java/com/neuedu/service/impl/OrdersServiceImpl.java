package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.domain.Orders;
import com.neuedu.mapper.OrdersMapper;
import com.neuedu.service.OrdersService;
import com.neuedu.vo.OrdersVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Override
    public IPage pageList(Page<Orders> page, OrdersVo ordersVo) {
        return baseMapper.pageList(page,ordersVo);
    }
    @Override
    public Integer getMaxId() {
        return baseMapper.getMaxId();
    }


}

package com.neuedu.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Address;
import com.neuedu.domain.Classify;
import com.neuedu.domain.Orders;
import com.neuedu.service.OrdersService;
import com.neuedu.tools.DateTools;
import com.neuedu.tools.Result;
import com.neuedu.vo.OrdersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:OrdersController
 * Package:com.shop.vueshop.controller
 * Description:
 * <p>
 * &#064;Author:  贤哥编程
 * &#064;Create:  2023-3-20 - 8:48
 */
@Api(tags = "订单信息")
@CrossOrigin
@RestController
@RequestMapping("/orders/api")
@Slf4j
public class OrdersController {
    @Resource
    private OrdersService ordersService;

    @ApiOperation(value = "修改订单信息", response = Classify.class)
    @PutMapping("/edit")
    public Result update(@RequestBody Orders orders) {
        orders.setUpdateTime(new Date());
        //调用物流方法
        //判断是否是否有物流用户是否收货。
        boolean update = ordersService.updateById(orders);
        if (update == false) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

    @ApiOperation(value = "订单下单", response = Orders.class)
    @PostMapping("/save")
    public Result add(@RequestBody Orders orders) {
        //设置添加时间
        orders.setCreateTime(new Date());
        //设置是否删除
        orders.setIsDelete(0);
        double doubleValue = orders.getOrdersPrice().doubleValue(); // 拿到价格
        Integer number = orders.getGoodsNumber(); // 拿到数量
        double price = number * doubleValue; // 算出总价
        orders.setOrdersPrice(BigDecimal.valueOf(price));
        //设置订单状态
        orders.setStatus(0);
        //订单编号生成
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date());
        Integer max = ordersService.getMaxId() + 1001;
        String str = date + max;
        orders.setOrdersNumber(str);
        boolean save = ordersService.save(orders);
        if (save == false) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    @ApiOperation(value = "查询地址信息分页", response = Address.class)
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result getAll(
            @PathVariable @ApiParam(name = "pageNum", value = "当前第几页") long pageNum,
            @PathVariable @ApiParam(name = "pageSize", value = "每页多少条数据") long pageSize,
            @RequestBody(required = false)
            OrdersVo ordersVo
    ) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getOrdersNumber())) {
            queryWrapper.like("orders_number", ordersVo.getOrdersNumber());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getGoodsName())) {
            queryWrapper.like("goods_name", ordersVo.getGoodsName());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getUsersPhone())) {
            queryWrapper.like("users_phone", ordersVo.getUsersPhone());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getUsersName())) {
            queryWrapper.like("users_name", ordersVo.getUsersName());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getStartTime())) {
            String strDate = DateTools.dealDateFormat(ordersVo.getStartTime());
            DateTime parse = DateUtil.parse(strDate);
            queryWrapper.ge("create_time", parse);
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getEndTime())) {
            String endDate = DateTools.dealDateFormat(ordersVo.getEndTime());
            DateTime parse = DateUtil.parse(endDate);
            queryWrapper.le("create_time", parse);
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(ordersVo.getStartPrice())) {
            queryWrapper.ge("orders_price", ordersVo.getStartPrice());
        }
        if (StringUtils.checkValNotNull(ordersVo.getEndPrice())) {
            queryWrapper.le("orders_price", ordersVo.getEndPrice());
        }
        queryWrapper.orderByDesc("orders_id");
        Page<Orders> ordersPage = ordersService.page(page, queryWrapper);
        return Result.ok(ordersPage);
    }

}

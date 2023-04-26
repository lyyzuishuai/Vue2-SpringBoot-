package com.neuedu.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Goods;
import com.neuedu.service.GoodsService;
import com.neuedu.tools.DateTools;
import com.neuedu.tools.Result;
import com.neuedu.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "商品信息")
@CrossOrigin
@RestController
@RequestMapping("/goods/api")
@Slf4j
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    //修改商品
    @ApiOperation(value = "修改商品", response = Goods.class)
    @PutMapping("/update")
    public Result update(@RequestBody Goods goods) {
        goods.setUpdateTime(new Date());
        boolean update = goodsService.updateById(goods);
        if (update == false) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

    //添加商品
    @ApiOperation(value = "添加商品", response = Goods.class)
    @PostMapping("/save")
    public Result add(@RequestBody Goods goods) {
        goods.setCreateTime(new Date());
        goods.setIsDelete(0);
        boolean save = goodsService.save(goods);
        if (save == false) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    //删除方法
    @ApiOperation(value = "按照id删除信息", response = Goods.class)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean remove = goodsService.removeById(id);
        if (remove == false) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }

    //查询条件分页数据
    @ApiOperation(value = "查询所有商品信息", response = Goods.class)
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result listAll(
            @PathVariable @ApiParam(name = "pageNum", value = "当前第几页") long pageNum,
            @PathVariable @ApiParam(name = "pageSize", value = "每页多少条数据") long pageSize,
            @RequestBody(required = false) GoodsVo goodsVo
    ) {
        Page<Goods> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        if (StringUtils.checkValNotNull(goodsVo.getGoodsName())) {
            queryWrapper.like("goods_name", goodsVo.getGoodsName());
        }
        if (StringUtils.checkValNotNull(goodsVo.getStartPrice())) {
            queryWrapper.ge("price", goodsVo.getStartPrice());
        }
        if (StringUtils.checkValNotNull(goodsVo.getEndPrice())) {
            queryWrapper.le("price", goodsVo.getEndPrice());
        }
        if (StringUtils.checkValNotNull(goodsVo.getStartTime())) {
            String strDate = DateTools.dealDateFormat(goodsVo.getStartTime());
            DateTime parse = DateUtil.parse(strDate);
            log.info("开始转化时间=" + parse);
            queryWrapper.ge("create_time", parse);
        }
        if (StringUtils.checkValNotNull(goodsVo.getEndTime())) {
            String strDate = DateTools.dealDateFormat(goodsVo.getEndTime());
            DateTime parse = DateUtil.parse(strDate);
            log.info("结束转化时间=" + parse);
            queryWrapper.le("create_time", parse);
        }
        queryWrapper.orderByDesc("goods_id");
        Page<Goods> list = goodsService.page(page, queryWrapper);

        return Result.ok(list);
    }

}

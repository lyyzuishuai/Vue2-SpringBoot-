package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Classify;
import com.neuedu.service.ClassifyService;
import com.neuedu.tools.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Api(tags = "商品分类信息")
@CrossOrigin
@RestController
@RequestMapping("/classify/api")
public class ClassifyController {
    @Resource
    private ClassifyService classifyService;

    @ApiOperation(value = "按照id删除信息", response = Classify.class)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean remove = classifyService.removeById(id);
        if (remove == false) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }

    /**
     * 修改信息
     *
     * @param classify
     * @return
     */
    @ApiOperation(value = "修改分类信息", response = Classify.class)
    @PutMapping("/edit")
    public Result update(@RequestBody Classify classify) {
        classify.setUpdateTime(new Date());
        boolean update = classifyService.updateById(classify);
        if (update == false) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

    /**
     * 添加分类
     *
     * @param classify
     * @return
     */
    @ApiOperation(value = "添加分类信息", response = Classify.class)
    @PostMapping("/add")
    public Result add(@RequestBody Classify classify) {
        classify.setCreateTime(new Date());
        boolean save = classifyService.save(classify);

        if (save == false) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    /**
     * 按照Id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "按照Id查询分类信息", response = Classify.class)
    @GetMapping("/list/{id}")
    public Result getListAll(
            @ApiParam(name = "id", value = "按照Id查询")
            @PathVariable
            Integer id
    ) {
        Classify classify = classifyService.getById(id);
        if (classify == null) {
            return Result.fail().message("没有查询到数据");
        }
        return Result.ok(classify);
    }

    /**
     * 查询所有分类数据
     *
     * @return
     */
    @ApiOperation(value = "查询所有商品分类信息", response = Classify.class)
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result getListAll(
            @PathVariable
            @ApiParam(name = "pageNum", value = "当前第几页")
            long pageNum,
            @PathVariable
            @ApiParam(name = "pageSize", value = "每页多少条数据")
            long pageSize,
            @RequestBody(required = false)
            Classify classify
    ) {

        Page<Classify> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Classify> queryWrapper = new QueryWrapper<>();
        if (StringUtils.checkValNotNull(classify.getClassifyName())) {
            queryWrapper.like("classify_name", classify.getClassifyName());
        }
        queryWrapper.orderByDesc("classify_id");

        Page list = classifyService.page(page, queryWrapper);

        if (list == null) {
            return Result.fail().message("查询失败");
        }
        return Result.ok(list);
    }

    /**
     * 按照名称查询
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "按分类名称查询", response = Classify.class)
    @GetMapping("/where/{name}")
    public Result getListName(
            @PathVariable
            @ApiParam(name = "name", value = "分类名称")
            String name) {
        QueryWrapper<Classify> queryWrapper = new QueryWrapper<>();
        if (StringUtils.checkValNotNull(name)) {
            queryWrapper.like("classify_name", name);
        }
        List<Classify> list = classifyService.list(queryWrapper);

        if (list == null) {
            return Result.fail().message("查询失败");
        }
        return Result.ok(list);
    }

    @ApiOperation(value = "查询所有分类", response = Classify.class)
    @GetMapping("/goods/list")
    public Result getListName() {

        List<Classify> list = classifyService.list();

        if (list == null) {
            return Result.fail().message("查询失败");
        }
        return Result.ok(list);
    }
}


package com.neuedu.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Users;
import com.neuedu.service.UsersService;
import com.neuedu.tools.MD5;
import com.neuedu.tools.Result;
import com.neuedu.vo.UsersVo;
import com.neuedu.domain.Goods;
import com.neuedu.tools.DateTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "用户信息")
@CrossOrigin
@RestController
@RequestMapping("/users/api")
@Slf4j
public class UsersController {
    @Resource
    private UsersService usersService;

    @ApiOperation(value = "查询所有用户", response = Users.class)
    @GetMapping("/users/list")
    public Result usersList() {
        List<Users> list = usersService.list();
        return Result.ok(list);
    }

    //修改用户
    @ApiOperation(value = "修改用户", response = Users.class)
    @PutMapping("/update")
    public Result update(@RequestBody Users users) {
        users.setUpdateTime(new Date());
        if (StringUtils.checkValNotNull(users.getPassword())) {
            String str = MD5.encrypt(users.getPassword());
            users.setPassword(str);
        }
        boolean update = usersService.updateById(users);
        if (update == false) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

    @ApiOperation(value = "添加用户", response = Goods.class)
    @PostMapping("/save")
    public Result add(@RequestBody Users users) {
        users.setCreateTime(new Date());
        users.setIsDelete(0);
        String password = MD5.encrypt(users.getPassword());
        users.setPassword(password);
        boolean save = usersService.save(users);
        if (save == false) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    @ApiOperation(value = "按照id删除信息", response = Users.class)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean remove = usersService.removeById(id);
        if (remove == false) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }

    //分页条件查询
    @ApiOperation(value = "查询所有用户信息", response = Goods.class)
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result listAll(
            @PathVariable @ApiParam(name = "pageNum", value = "当前第几页") long pageNum,
            @PathVariable @ApiParam(name = "pageSize", value = "每页多少条数据") long pageSize,
            @RequestBody(required = false) UsersVo usersVo
    ) {
        Page<Users> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        if (StringUtils.checkValNotNull(usersVo.getUsername())) {
            queryWrapper.like("username", usersVo.getUsername());
        }
        if (StringUtils.checkValNotNull(usersVo.getNickname())) {
            queryWrapper.like("nickname", usersVo.getNickname());
        }
        if (StringUtils.checkValNotNull(usersVo.getStartTime())) {
            String strDate = DateTools.dealDateFormat(usersVo.getStartTime());
            DateTime parse = DateUtil.parse(strDate);
            log.info("开始转化时间=" + parse);
            queryWrapper.ge("create_time", parse);
        }
        if (StringUtils.checkValNotNull(usersVo.getEndTime())) {
            String strDate = DateTools.dealDateFormat(usersVo.getEndTime());
            DateTime parse = DateUtil.parse(strDate);
            log.info("结束转化时间=" + parse);
            queryWrapper.le("create_time", parse);
        }
        queryWrapper.orderByDesc("users_id");
        Page<Users> usersPage = usersService.page(page, queryWrapper);
        if (usersPage == null) {
            return Result.fail().message("未查到数据");
        }
        return Result.ok(usersPage);
    }
}

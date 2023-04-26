package com.neuedu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.domain.Address;
import com.neuedu.domain.Users;
import com.neuedu.service.AddressService;
import com.neuedu.tools.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "地址信息信息")
@CrossOrigin
@RestController
@RequestMapping("/address/api")
@Slf4j
public class AddressController {
    @Resource
    private AddressService addressService;

    @ApiOperation(value = "修改商品", response = Address.class)
    @PutMapping("/update")
    public Result update(@RequestBody Address address) {
        address.setUpdateTime(new Date());
        boolean update = addressService.updateById(address);
        if (update == false) {
            return Result.fail().message("修改失败");
        }
        return Result.ok().message("修改成功");
    }

    @ApiOperation(value = "添加用户", response = Address.class)
    @PostMapping("/save")
    public Result add(@RequestBody Address address) {
        address.setCreateTime(new Date());
        address.setIsDelete(0);
        boolean save = addressService.save(address);
        if (save == false) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    @ApiOperation(value = "按照id删除信息", response = Address.class)
    @DeleteMapping("/delete/{addressId}")
    public Result delete(@PathVariable Integer addressId) {
        boolean remove = addressService.removeById(addressId);
        //log.info("删除="+remove);
        if (remove == false) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }

    @ApiOperation(value = "查询地址信息分页", response = Address.class)
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result getAll(
            @PathVariable @ApiParam(name = "pageNum", value = "当前第几页") long pageNum,
            @PathVariable @ApiParam(name = "pageSize", value = "每页多少条数据") long pageSize,
            @RequestBody(required = false)
            Address address
    ) {
        Page<Address> page = new Page<>(pageNum, pageSize);

        if (StringUtils.checkValNotNull(address.getUsersName())) {
            String usersName = "%" + address.getUsersName() + "%";
            address.setUsersName(usersName);
        }
        if (StringUtils.checkValNotNull(address.getUsersPhone())) {
            String phone = "%" + address.getUsersPhone() + "%";
            address.setUsersPhone(phone);
        }
        if (StringUtils.checkValNotNull(address.getUsers())) {
            Users u = new Users();
            if (StringUtils.checkValNotNull(address.getUsers().getUsername())) {
                String user = "%" + address.getUsers().getUsername() + "%";
                u.setUsername(user);
            }
            if (StringUtils.checkValNotNull(address.getUsers().getNickname())) {
                String pass = "%" + address.getUsers().getNickname() + "%";
                u.setNickname(pass);
            }
            address.setUsers(u);
        }


        IPage pageList = addressService.getAll(page, address);
        if (pageList == null) {
            return Result.fail().message("未查到你要的数据");
        }
        return Result.ok(pageList);
    }
}

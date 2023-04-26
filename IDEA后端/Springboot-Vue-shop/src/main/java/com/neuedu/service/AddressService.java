package com.neuedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.domain.Address;


public interface AddressService extends IService<Address> {
    IPage getAll(Page<Address> page, Address address);

}

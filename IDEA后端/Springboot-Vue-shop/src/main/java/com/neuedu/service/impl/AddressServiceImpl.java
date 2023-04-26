package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.domain.Address;
import com.neuedu.mapper.AddressMapper;
import com.neuedu.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Override
    public IPage getAll(Page<Address> page, Address address) {
        return baseMapper.getAll(page, address);
    }
}

package com.neuedu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.domain.Users;
import com.neuedu.mapper.UsersMapper;

import com.neuedu.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
}

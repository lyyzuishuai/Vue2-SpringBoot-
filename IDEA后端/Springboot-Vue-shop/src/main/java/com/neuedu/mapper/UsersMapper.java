package com.neuedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuedu.domain.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}

package com.neuedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.domain.Goods;
import com.neuedu.mapper.GoodsMapper;
import com.neuedu.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}

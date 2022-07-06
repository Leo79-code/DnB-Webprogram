package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
}

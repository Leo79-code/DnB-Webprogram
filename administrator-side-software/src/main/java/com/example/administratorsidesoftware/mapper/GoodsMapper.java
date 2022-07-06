package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    //TODO 找到某一manager对应的所有goods（动态SQL）
}

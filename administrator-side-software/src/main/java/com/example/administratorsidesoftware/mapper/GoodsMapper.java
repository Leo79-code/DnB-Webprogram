package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.administratorsidesoftware.common.GoodsType;

import com.example.administratorsidesoftware.entity.Goods;
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> findGoodsPageByWarehouse(IPage<Goods> page, Integer warehouseNo);

    IPage<Goods> findGoodsPage(IPage<Goods> page, Integer goodsId, GoodsType color, Integer managerId);

    Goods findGoodsIdByPositionNo(Integer positionNo);
}

package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.administratorsidesoftware.common.GoodsType;

import com.example.administratorsidesoftware.entity.Goods;



public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 分页查找某个warehouse中的全部goods
     * @param page 将查询结果储存到该类，其中带有pageNum和pageSize信息
     * @param warehouseNo 要查询的warehouse的warehouseNo
     * @return 查询得到的结果页
     */
    IPage<Goods> findGoodsPageByWarehouse(IPage<Goods> page, Integer warehouseNo);

    /**
     * 分页多条件查找某个manager的goods
     * @param page 将查询结果储存到该类，其中带有pageNum和pageSize信息
     * @param goodsId 根据goodsId查找时需要，可不输入
     * @param color 根据color查找时需要，可不输入
     * @param managerId 进行查找操作的manager的Id，必须输入
     * @return 查询得到的结果页
     */

    IPage<Goods> findGoodsPage(IPage<Goods> page, Integer goodsId, GoodsType color, Integer managerId);

    /**
     * 根据positionNo查找对应的goods
     * @param positionNo 需要查找的position的positionNo
     * @return 该position储存的goods
     */
    Goods findGoodsIdByPositionNo(Integer positionNo);
}

package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
    @Autowired
    private PositionService positionService;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PositionMapper positionMapper;

    public boolean deleteGoodsById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.isAvailable()) {
            goods.setAvailable(false);
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtil.copyProperties(goods, goodsDTO);
            return positionService.takeout(goodsDTO) && updateById(goods);
        } else {
            return false;
        }
    }

    public boolean updatePosition(GoodsDTO goodsDTO) {
        Goods goods = new Goods();
        BeanUtil.copyProperties(goodsDTO, goods);
        goods.setAvailable(true);
        Goods goods1 = goodsMapper.selectById(goodsDTO.getGoodsId());
        Position position = positionMapper.selectById(goods1.getPositionNo());
        if (positionService.takeout(goodsDTO)) {
            if(positionService.putin(goodsDTO)){
                return updateById(goods);
            }else {
                goodsDTO.setPositionNo(position.getPositionNo());
                positionService.putin(goodsDTO);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateColor(GoodsDTO goodsDTO) {
        Goods goods = goodsMapper.selectById(goodsDTO.getGoodsId());
        if(goods.isAvailable()){
            Goods goods1 = new Goods();
            BeanUtil.copyProperties(goodsDTO,goods1);
            return updateById(goods1);
        }else {
            return false;
        }
    }

    public boolean addGoods(GoodsDTO goodsDTO) {
        if(positionService.putin(goodsDTO)){
            Goods goods = new Goods();
            BeanUtil.copyProperties(goodsDTO,goods);
            goods.setAvailable(true);
            return goodsMapper.insert(goods)==1;
        }else {
            return false;
        }
    }
}

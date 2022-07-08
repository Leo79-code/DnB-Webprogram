package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
    @Autowired
    private PositionService positionService;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
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
        if((goodsMapper.selectById(goodsDTO.getGoodsId()) != null)|| goodsDTO.getGoodsId()<=0 ){
            return false;
        };
        if(positionService.putin(goodsDTO)){
            Goods goods = new Goods();
            BeanUtil.copyProperties(goodsDTO,goods);
            goods.setAvailable(true);
            return goodsMapper.insert(goods)==1;
        }else {
            return false;
        }
    }

    public IPage<Goods> findGoodsPageByWarehouse(IPage<Goods> page, Integer warehouseNo) {
        return goodsMapper.findGoodsPageByWarehouse(page,warehouseNo);
    }

    public IPage<Goods> findGoodsPage(IPage<Goods> page, Integer goodsId, GoodsType color, Integer managerId) {
        return goodsMapper.findGoodsPage(page,goodsId,color,managerId);
    }
}

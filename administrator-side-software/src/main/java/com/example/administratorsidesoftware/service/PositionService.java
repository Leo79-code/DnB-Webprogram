package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.mapper.ManagerMapper;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> {

    @Resource
    private PositionMapper positionMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private ManagerMapper managerMapper;


    public boolean putin(GoodsDTO goodsDTO) {
        //goodsDTO中position是要放入的地方
        Position position = positionMapper.selectById(goodsDTO.getPositionNo());
        //要放入的地方
        if (position.isAvailable() &&
                (managerMapper.getPositionManager(position.getPositionNo()).getManagerId().equals(goodsDTO.getManagerId()))) {
            position.setAvailable(false);
            return updateById(position);
        } else {
            return false;
        }
    }

    public boolean takeout(GoodsDTO goodsDTO) {
        //goodsDTO中positionNo是要放入的地方
        Goods goods = goodsMapper.selectById(goodsDTO.getGoodsId());
        //拿到 拿出前的货物
        if (goods.isAvailable()) {
            Position position = positionMapper.selectById(goods.getPositionNo());
            //拿出的位置
            position.setAvailable(true);
            return updateById(position);
        } else {
            return false;
        }
    }


}

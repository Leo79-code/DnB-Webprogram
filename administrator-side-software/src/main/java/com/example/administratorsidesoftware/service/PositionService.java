package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.common.Constant;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.controller.DTO.PositionDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.mapper.ManagerMapper;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import com.example.administratorsidesoftware.mapper.RecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> {

    @Resource
    private PositionMapper positionMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private ManagerMapper managerMapper;

    @Resource
    private RecordService recordService;

    @Resource
    private RecordMapper recordMapper;


    public boolean putin(GoodsDTO goodsDTO) {
        //goodsDTO中position是要放入的地方
        Position position = positionMapper.selectById(goodsDTO.getPositionNo());
        //要放入的地方
        if (position.isAvailable() &&
                (managerMapper.getPositionManager(position.getPositionNo()).getManagerId().equals(goodsDTO.getManagerId()))) {
            position.setAvailable(false);
            recordService.saveNewRecord(goodsDTO.getPositionNo(), goodsDTO.getGoodsId());
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
            Record record = recordMapper.searchRecord(goodsDTO.getGoodsId(), position.getPositionNo());
            record.takeout();
            position.setAvailable(true);
            return updateById(position) && recordMapper.updateById(record) == 1;
        } else {
            return false;
        }
    }


    public boolean saveWarehouse(Warehouse warehouse) {
        boolean result = true;
        for (int i = 0; i < Constant.DEFAULT_POSITION_NUM; i++) {
            Position position = new Position();
            position.setWarehouseNo(warehouse.getWarehouseNo());
            position.setAvailable(true);
            result = result && save(position);
        }
        return result;
    }

    public boolean removeWarehouse(Integer warehouseNo) {
        boolean result = true;
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        List<Position> positions = positionMapper.selectList(queryWrapper);
        for (Position position : positions) {
            result = result && deletePosition(position);
        }
        return result;
    }

    private boolean deletePosition(Position position) {
        if (position.isAvailable()) {
            return removeById(position);
        } else {
            return false;
        }
    }

    public List<PositionDTO> listPositionDTOByWarehouse(Integer warehouseNo) {
        List<PositionDTO> positionDTOList = new ArrayList<>();
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        List<Position> positions = positionMapper.selectList(queryWrapper);
        for (Position position : positions) {
            PositionDTO positionDTO = new PositionDTO();
            BeanUtil.copyProperties(position, positionDTO);
            if (!positionDTO.isAvailable()){
                positionDTO.setGoodsId(goodsMapper.findGoodsIdByPositionNo(positionDTO.getPositionNo()).getGoodsId());
            }
            positionDTOList.add(positionDTO);
        }
        return positionDTOList;
    }

    public List<Position> listPositionByWarehouse(Integer warehouseNo) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo",warehouseNo);
        return positionMapper.selectList(queryWrapper);
    }
}

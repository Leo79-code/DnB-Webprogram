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

    /**
     * 将goods放入position，创建record
     * @param goodsDTO goodsId，positionNo，managerId
     * @return 放入成功true，失败false
     */
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

    /**
     * 从position中拿出goods，并更新record
     * @param goodsDTO goodsId，positionNo
     * @return 拿出成功true，失败false
     */
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

    /**
     * 创建warehouse时自动创建默认数量个position
     * @param warehouse 创建的warehouse，warehouseNo
     * @return 创建成功true，失败false
     */
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

    /**
     * 删除warehouse时删除所有它的position
     * @param warehouseNo 要删除的warehouseNo
     * @return 删除成功true，失败false
     */
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

    /**
     * 删除某个position
     * @param position available，positionNo
     * @return 删除成功true，失败false
     */
    private boolean deletePosition(Position position) {
        if (position.isAvailable()) {
            return removeById(position);
        } else {
            return false;
        }
    }

    /**
     * 查询某warehouse的goods+position信息
     * @param warehouseNo 要查询的warehouse的warehouseNo
     * @return 返回的list中包含每个position的positionNo，available；如果有goods则还会返回goodsId，color
     */
    public List<PositionDTO> listPositionDTOByWarehouse(Integer warehouseNo) {
        List<PositionDTO> positionDTOList = new ArrayList<>();
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        List<Position> positions = positionMapper.selectList(queryWrapper);
        for (Position position : positions) {
            PositionDTO positionDTO = new PositionDTO();
            BeanUtil.copyProperties(position, positionDTO);
            if (positionDTO.isAvailable()){
                positionDTO.setStrAvailable("true");
            }else {
                positionDTO.setStrAvailable("false");
                positionDTO.setGoodsId(goodsMapper.findGoodsIdByPositionNo(positionDTO.getPositionNo()).getGoodsId());
                positionDTO.setColor(goodsMapper.findGoodsIdByPositionNo(positionDTO.getPositionNo()).getColor());
            }
            positionDTOList.add(positionDTO);
        }
        return positionDTOList;
    }

    /**
     * 找到某个warehouse的所有position
     * @param warehouseNo 待查找的warehouse的warehouseNo
     * @return list中包含该warehouse的全部position信息，包括positionNo，available
     */
    public List<Position> listPositionByWarehouse(Integer warehouseNo) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo",warehouseNo);
        return positionMapper.selectList(queryWrapper);
    }

    /**
     * 找到某个warehouse所有非空position
     * @param warehouseNo 待查找的warehouse的warehouseNo
     * @return list中包含全部非空position
     */
    public List<Position> listNonEmptyPositionByWarehouse(Integer warehouseNo) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo",warehouseNo);
        queryWrapper.eq("available",false);
        return positionMapper.selectList(queryWrapper);
    }
}

package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.WarehouseDTO;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class WarehouseService extends ServiceImpl<WarehouseMapper, Warehouse> {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private PositionService positionService;

    /**
     * 找不到workerId时创建worker，找得到时更新worker
     * @param warehouseDTO 更新时传入warehouseNo和要更新的信息，创建时不用传入warehouseNo
     * @return 成功返回true，失败返回false
     */
    public boolean saveOrUpdateWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtil.copyProperties(warehouseDTO, warehouse);
        if (warehouse.getWarehouseNo() != null) {
            if (warehouseMapper.selectById(warehouse.getWarehouseNo()) != null) {
                return updateById(warehouse);
            } else {
                return save(warehouse) && positionService.saveWarehouse(warehouse);
            }
        } else {
            return save(warehouse) && positionService.saveWarehouse(warehouse);
        }
    }

    /**
     * 删除warehouse
     * @param warehouseNo 要删除的warehouseNo
     * @return 删除成功返回true，失败返回false
     */
    public boolean removeWarehouse(Integer warehouseNo) {

        List<Position> positions = positionService.listNonEmptyPositionByWarehouse(warehouseNo);
        //有东西的position列表
        if (positions.isEmpty()) {
            return positionService.removeWarehouse(warehouseNo) && removeById(warehouseNo);
        } else {
            return false;
        }
    }
}

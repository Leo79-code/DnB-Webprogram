package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.WarehouseDTO;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import com.example.administratorsidesoftware.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;


@Service
public class WarehouseService extends ServiceImpl<WarehouseMapper, Warehouse> {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private PositionService positionService;

    public boolean saveOrUpdateWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtil.copyProperties(warehouseDTO, warehouse);
        if(warehouse.getWarehouseNo() != null){
            if(warehouseMapper.selectById(warehouse.getWarehouseNo()) != null){
                return updateById(warehouse);
            }else {
               return save(warehouse) && positionService.saveWarehouse(warehouse);
            }
        } else {
           return save(warehouse) && positionService.saveWarehouse(warehouse);
        }
    }

    public boolean removeWarehouse(Integer warehouseNo) {
        return positionService.removeWarehouse(warehouseNo) && removeById(warehouseNo);
    }
}

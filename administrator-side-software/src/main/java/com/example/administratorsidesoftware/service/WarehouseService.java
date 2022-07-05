package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends ServiceImpl<WarehouseMapper, Warehouse> {
    public boolean saveOrUpdateWarehouse(Warehouse warehouse) {
        //TODO  不输入managerId和warehouseNo情况下按原始值更新仓库
        return false;
    }
}

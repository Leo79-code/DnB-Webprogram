package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.WarehouseDTO;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
public class WarehouseService extends ServiceImpl<WarehouseMapper, Warehouse> {
    public boolean saveOrUpdateWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtil.copyProperties(warehouseDTO, warehouse);
        return saveOrUpdate(warehouse);
    }
}

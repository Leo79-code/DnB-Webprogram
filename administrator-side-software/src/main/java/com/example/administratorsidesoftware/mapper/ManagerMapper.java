package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Manager;

public interface ManagerMapper extends BaseMapper<Manager> {
    Manager getPositionManager(Integer positionNo);
}

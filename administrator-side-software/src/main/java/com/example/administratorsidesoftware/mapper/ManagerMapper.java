package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.entity.Position;

public interface ManagerMapper extends BaseMapper<Manager> {
    Manager getPositionManager(Integer positionNo);
}

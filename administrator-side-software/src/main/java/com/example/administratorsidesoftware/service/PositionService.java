package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.controller.DTO.PositionDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import org.springframework.stereotype.Service;

@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> {
    public boolean updatePosition(PositionDTO positionDTO) {
        Position position = new Position();
        BeanUtil.copyProperties(positionDTO,position);
        return updateById(position);
    }
}

package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Manager;

public interface ManagerMapper extends BaseMapper<Manager> {
    /**
     * 得到某一position所在的warehouse的manager的Id
     * @param positionNo 需要查询的position
     * @return 拥有该position的manager
     */
    Manager getPositionManager(Integer positionNo);
}

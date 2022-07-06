package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper
public interface PositionMapper extends BaseMapper<Position> {
}

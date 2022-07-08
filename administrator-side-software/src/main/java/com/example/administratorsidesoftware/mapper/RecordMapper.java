package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Record;

import java.util.List;

public interface RecordMapper extends BaseMapper<Record> {
    Record searchRecord(Integer goodsId, Integer positionNo);

    List<Record> searchRecordByWarehouse(Integer warehouseNo);
}

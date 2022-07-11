package com.example.administratorsidesoftware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.administratorsidesoftware.entity.Record;

import java.util.List;

public interface RecordMapper extends BaseMapper<Record> {
    /**
     * 拿出货物时根据goodsId和positionNo，查找需要更新的record
     * @param goodsId 查询的goodsId
     * @param positionNo 查询的positionNo
     * @return 需要更新的record
     */
    Record searchRecord(Integer goodsId, Integer positionNo);

    /**
     * 根据warehouse查找所有属于该warehouse的record
     * @param warehouseNo 查询的warehouseNo
     * @return 查询到的所有record组成的list
     */
    List<Record> searchRecordByWarehouse(Integer warehouseNo);
}

package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.mapper.RecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordService extends ServiceImpl<RecordMapper, Record> {
    @Resource
    private RecordMapper recordMapper;

    public boolean saveNewRecord(Integer positionNo, Integer goodsId, GoodsType color, Integer warehouseNo){
        Record record = new Record(positionNo, goodsId, color, warehouseNo);
        return recordMapper.insert(record) ==1;
    }

    public List<Record> searchRecordByWarehouse(Integer warehouseNo) {
        return recordMapper.searchRecordByWarehouse(warehouseNo);
    }
}

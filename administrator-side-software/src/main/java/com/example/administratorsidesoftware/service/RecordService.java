package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.mapper.RecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordService extends ServiceImpl<RecordMapper, Record> {
    @Resource
    private RecordMapper recordMapper;

    /**
     * 创建新record
     * @param positionNo 储存到的position的positionNo
     * @param goodsId 要储存的goods的goodsId
     * @return 创建成功true，失败false
     */
    public boolean saveNewRecord(Integer positionNo, Integer goodsId){
        Record record = new Record(positionNo, goodsId);
        return recordMapper.insert(record) ==1;
    }

    /**
     * 查询某warehouse全部record
     * @param warehouseNo 待查询的warehouse的warehouseNo
     * @return 该warehouse的全部record的list
     */
    public List<Record> searchRecordByWarehouse(Integer warehouseNo) {
        return recordMapper.searchRecordByWarehouse(warehouseNo);
    }
}

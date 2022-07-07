package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.mapper.RecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecordService extends ServiceImpl<RecordMapper, Record> {
    @Resource
    private RecordMapper recordMapper;

    public boolean saveNewRecord(Integer positionNo, Integer goodsId){
        Record record = new Record(positionNo, goodsId);
        return recordMapper.insert(record) ==1;
    }
}

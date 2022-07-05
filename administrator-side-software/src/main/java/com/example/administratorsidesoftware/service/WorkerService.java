package com.example.administratorsidesoftware.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.mapper.WorkerMapper;
import org.springframework.stereotype.Service;


@Service
public class WorkerService extends ServiceImpl<WorkerMapper, Worker> {
    public boolean saveOrUpdateWorker(Worker worker) {
        //TODO 不输入managerId和warehouseNo情况下按原始值更新工人
        return false;
    }
}

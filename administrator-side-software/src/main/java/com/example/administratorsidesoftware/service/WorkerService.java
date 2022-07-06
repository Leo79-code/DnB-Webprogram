package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.WorkerDTO;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.mapper.WorkerMapper;
import org.springframework.stereotype.Service;


@Service
public class WorkerService extends ServiceImpl<WorkerMapper, Worker> {
    public boolean saveOrUpdateWorker(WorkerDTO workerDTO) {
        Worker worker = new Worker();
        BeanUtil.copyProperties(workerDTO, worker);
        worker.setEmployed(true);
        return saveOrUpdate(worker);
    }

    public boolean deleteWorkerById(Integer id) {
        UpdateWrapper<Worker> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("workerId",id);
        updateWrapper.set("isEmployed",false);
        return update(null,updateWrapper);
    }
}

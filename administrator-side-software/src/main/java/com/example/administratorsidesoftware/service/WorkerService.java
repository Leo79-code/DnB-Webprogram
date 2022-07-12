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
    /**
     * 找不到workerId时创建worker，找得到时更新worker
     * @param workerDTO 更新时传入workerId，要更新的信息；创建时不用传入workerId
     * @return 更新成功true，失败false
     */
    public boolean saveOrUpdateWorker(WorkerDTO workerDTO) {
        Worker worker = new Worker();
        BeanUtil.copyProperties(workerDTO, worker);
        worker.setEmployed(true);
        return saveOrUpdate(worker);
    }

    /**
     * 删除worker（isEmployed为false）
     * @param id 要删除的worker的id
     * @return 删除成功true，失败false
     */
    public boolean deleteWorkerById(Integer id) {
        UpdateWrapper<Worker> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("workerId",id);
        updateWrapper.set("isEmployed",false);
        return update(null,updateWrapper);
    }
}

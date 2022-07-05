package com.example.administratorsidesoftware.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.mapper.WorkerMapper;
import com.example.administratorsidesoftware.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("worker/")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    /**
     * Paging shows all workers in a warehouse
     * @param pageNum The sequence number of the page displayed
     * @param pageSize Maximum number of items that can be displayed per page
     * @param warehouseNo The number of the warehouse being queried
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/warehouse/page")
    public IPage<Worker> listWorkerPageByWarehouse(@RequestParam Integer pageNum,
                                                   @RequestParam Integer pageSize,
                                                   @RequestParam int warehouseNo) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        return workerService.page(page, queryWrapper);
    }

    /**
     * Paging shows all workers in an experience management
     * @param pageNum The sequence number of the page displayed
     * @param pageSize Maximum number of items that can be displayed per page
     * @param managerId The id of the manager being queried
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/manager/page")
    public IPage<Worker> listWorkerPageByManager(@RequestParam Integer pageNum,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam int managerId) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerId);
        return workerService.page(page,queryWrapper);
    }

    /**
     * Change the state of the worker or add worker.
     * @param worker worker should be changed or created.
     * @return true if the operation is successfully done.
     */
    @PostMapping("/")
    public boolean saveOrUpdateWorker(@RequestBody Worker worker){
        return workerService.saveOrUpdateWorker(worker);
    }

}

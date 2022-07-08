package com.example.administratorsidesoftware.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.WorkerDTO;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.mapper.WarehouseMapper;
import com.example.administratorsidesoftware.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("worker/")
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @Resource
    private WarehouseMapper warehouseMapper;

    /**
     * Paging shows all workers in a warehouse
     *
     * @param pageNum     The sequence number of the page displayed
     * @param pageSize    Maximum number of items that can be displayed per page
     * @param warehouseNo The number of the warehouse being queried
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/warehouse/{warehouseNo}/list/page")
    public Result listWorkerPageByWarehouse(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @PathVariable Integer warehouseNo) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        queryWrapper.eq("isEmployed", true);
        return Result.success(workerService.page(page, queryWrapper));
    }

    /**
     * Paging shows all workers managed by current manager
     *
     * @param pageNum  The sequence number of the page displayed
     * @param pageSize Maximum number of items that can be displayed per page
     * @param managerId
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/manager/list/page")
    public Result listWorkerPageByManager(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam Integer managerId) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerId);
        queryWrapper.eq("isEmployed", true);
        return Result.success(workerService.page(page, queryWrapper));
    }

    /**
     * Change the state of the worker or add worker.
     *
     * @param workerDTO worker data transfer object. 禁止manager更改worker password
     * @return
     */
    @PostMapping("/change")
    public Result saveOrUpdateWorker(@RequestBody WorkerDTO workerDTO) {
        Warehouse warehouse = warehouseMapper.selectById(workerDTO.getWarehouseNo());
        if(warehouse == null){
            return Result.error("user error",false);
        }
        boolean result = workerService.saveOrUpdateWorker(workerDTO);
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("user error", false);
        }
    }

    /**
     * 通过id删除worker，将isEmplyed置为false
     *
     * @param id 要删除的worker的id
     * @return 成功返回success状态，失败返回business_error状态
     */
    @DeleteMapping("/{id}")
    public Result deleteWorker(@PathVariable Integer id) {
        boolean result = workerService.deleteWorkerById(id);
        if (result) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @GetMapping("/find/page")
    public Result findWorkerPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String workerName,
                                 @RequestParam(required = false) Integer workerId,
                                 @RequestParam Integer managerId) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId",managerId);
        queryWrapper.eq("isEmployed",true);
        if(workerId != null){
            queryWrapper.eq("workerId",workerId);
            return Result.success(workerService.page(page,queryWrapper));
        }else if(!"".equals(workerName)){
            queryWrapper.like("workerName",workerName);
            return Result.success(workerService.page(page,queryWrapper));
        }else {
            return Result.error("empty input");
        }
    }
}

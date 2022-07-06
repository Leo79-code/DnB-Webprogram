package com.example.administratorsidesoftware.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.WorkerDTO;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

@RestController
@RequestMapping("worker/")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

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
        return Result.success(workerService.page(page, queryWrapper));
    }

    /**
     * Paging shows all workers managed by current manager
     *
     * @param pageNum  The sequence number of the page displayed
     * @param pageSize Maximum number of items that can be displayed per page
     * @param session  session information
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/manager/list/page")
    public Result listWorkerPageByManager(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          HttpSession session) {
        IPage<Worker> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", session.getAttribute("managerId"));
        return Result.success(workerService.page(page, queryWrapper));
    }

    /**
     * Change the state of the worker or add worker.
     *
     * @param workerDTO worker data transfer object. 禁止manager更改worker password
     * @return true if the operation is successfully done.
     */
    @PostMapping("/change")
    public Result saveOrUpdateWorker(@RequestBody WorkerDTO workerDTO) {
        boolean result = workerService.saveOrUpdateWorker(workerDTO);
        if(result){
            return Result.success(true);
        }else {
            return Result.error("user error",false);
        }
    }
}

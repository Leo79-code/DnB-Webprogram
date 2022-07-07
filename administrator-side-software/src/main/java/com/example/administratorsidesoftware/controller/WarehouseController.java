package com.example.administratorsidesoftware.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.WarehouseDTO;
import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("warehouse/")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    /**
     * if there is a warehouse with the same warehouseId, update it; if there isn't, create it.
     *
     * @param warehouseDTO the warehouse should be updated or want to create.
     * @return true if the operation is successfully done.
     */
    @PostMapping("/change")
    public Result saveOrUpdateWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        boolean result = warehouseService.saveOrUpdateWarehouse(warehouseDTO);
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("user error", false);
        }
    }

    /**
     * delete a warehouse by warehouse number.
     *
     * @param warehouseNo number of the warehouse you want to delete
     * @return true if the operation is successfully done.
     */
    @DeleteMapping("/{warehouseNo}")
    public Result delete(@PathVariable Integer warehouseNo) {
        boolean result = warehouseService.removeById(warehouseNo);
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("user error", false);
        }
    }

    /**
     * Paging shows all warehouse managed by current manager
     *
     * @param pageNum  The sequence number of the page displayed
     * @param pageSize Maximum number of items that can be displayed per page
     * @param managerId  session information
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/manager/list/page")
    public Result listWarehousePageByManager(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam Integer managerId) {
        IPage<Warehouse> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerId);
        return Result.success(warehouseService.page(page, queryWrapper));
    }

    @GetMapping("/find/page")
    public Result findWarehousePage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String warehouseName,
                                    @RequestParam(required = false)Integer warehouseId,
                                    @RequestParam Integer managerId) {
        IPage<Warehouse> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerId);
        if (warehouseId != null) {
            queryWrapper.eq("warehouseId",warehouseId);
            return Result.success(warehouseService.page(page,queryWrapper));
        } else if (!"".equals(warehouseName)) {
            queryWrapper.like("warehouseName", warehouseName);
            return Result.success(warehouseService.page(page, queryWrapper));
        } else {
            return Result.error("empty input");
        }
    }
}

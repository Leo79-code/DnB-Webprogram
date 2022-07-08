package com.example.administratorsidesoftware.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.PositionDTO;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.entity.Worker;
import com.example.administratorsidesoftware.service.PositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("position/")
public class PositionController {

    @Resource
    private PositionService positionService;

    /**
     * Paging shows all workers in a warehouse
     *
     * @param pageNum     The sequence number of the page displayed
     * @param pageSize    Maximum number of items that can be displayed per page
     * @param warehouseNo The number of the warehouse being queried
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/warehouse/{warehouseNo}/list/page")
    public Result listPositionPageByWarehouse(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @PathVariable Integer warehouseNo) {
        IPage<PositionDTO> page = new Page<>(pageNum, pageSize);
        List<PositionDTO> positionDTOList = positionService.listPositionPageByWarehouse(warehouseNo);
        List<PositionDTO> positionDTOPage = new ArrayList<>();
        positionDTOPage.addAll(positionDTOList.subList((pageNum-1)*pageSize, (pageNum-1)*pageSize + pageSize));
        page.setRecords(positionDTOPage);
        page.setTotal(positionDTOList.size());
        page.setCurrent(pageNum);
        return Result.success(page);
    }

}
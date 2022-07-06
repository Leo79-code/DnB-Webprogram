package com.example.administratorsidesoftware.controller;

import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.controller.DTO.PositionDTO;
import com.example.administratorsidesoftware.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("position/")
public class PositionController {
    @Autowired
    private PositionService positionService;

    /**
     * 更新position
     * @param positionDTO position data transfer object，不能更改warehouseId
     * @return
     */
    @PostMapping("/update")
    public Result updatePosition(PositionDTO positionDTO){
        boolean result = positionService.updatePosition(positionDTO);
        if(result) {
            return Result.success(true);
        } else {
            return Result.error("user error",false);
        }
    }
}

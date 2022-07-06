package com.example.administratorsidesoftware.controller.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class WarehouseDTO {
    private Integer warehouseNo;
    private String warehouseName;
    private Integer managerId;
}


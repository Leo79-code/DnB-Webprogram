package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "Warehouse")
public class Warehouse {
    @TableId(value = "warehouseNo")
    private Integer warehouseNo;
    @TableField(value = "warehouseName")
    private String warehouseName;
    @TableField(value = "managerId")
    private Integer managerId;
}


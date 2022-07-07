package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "warehouse")
public class Warehouse {
    @TableId(value = "warehouseNo",type = IdType.AUTO)
    private Integer warehouseNo;
    @TableField(value = "warehouseName")
    private String warehouseName;
    @TableField(value = "managerId")
    private Integer managerId;
}


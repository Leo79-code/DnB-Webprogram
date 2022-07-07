package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "Position")
public class Position {
    @TableId(value = "positionNo")
    private Integer positionNo;
    @TableField(value = "available")
    private boolean available;
    @TableField(value = "warehouseNo")
    private Integer warehouseNo;
}

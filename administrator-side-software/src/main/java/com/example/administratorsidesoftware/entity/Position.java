package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "position")
public class Position {
    @TableId(value = "positionNo",type = IdType.AUTO)
    private Integer positionNo;
    @TableField(value = "available")
    private boolean available;
    @TableField(value = "warehouseNo")
    private Integer warehouseNo;
}

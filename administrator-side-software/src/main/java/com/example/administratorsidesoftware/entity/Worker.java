package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "worker")
public class Worker {

    @TableId(value = "workerId",type = IdType.AUTO)
    private Integer workerId;
    @TableField(value = "workerName")
    private String workerName;
    @TableField(value = "workerPassword")
    private String workerPassword;
    @TableField(value = "managerId")
    private Integer managerId;
    @TableField(value = "warehouseNo")
    private Integer warehouseNo;
    @TableField(value = "isEmployed")
    private boolean isEmployed;
}

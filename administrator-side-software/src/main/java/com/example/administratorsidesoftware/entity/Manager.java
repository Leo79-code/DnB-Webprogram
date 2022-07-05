package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "Manager")
public class Manager {
    @TableId(value = "ManagerID")
    private int managerId;
    @TableField(value = "ManagerPassword")
    private String managerPassword;
    @TableField(value = "ManagerName")
    private String managerName;
}

package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "manager")
public class Manager {
    @TableId(value = "managerId",type = IdType.AUTO)
    private Integer managerId;
    @TableField(value = "managerPassword")
    private String managerPassword;
    @TableField(value = "managerName")
    private String managerName;
    @TableField(value = "managerEmail")
    private String managerEmail;
    @TableField(value = "managerMobile")
    private String managerMobile;


    //@TableField(value = "avatar_url")
    private String avatar_url;
}

package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.administratorsidesoftware.common.GoodsType;
import lombok.Data;

@Data
@TableName(value = "Goods")
public class Goods {
    @TableId(value = "goodsId")
    private Integer goodsId;
    @TableField(value = "color")
    private GoodsType color;
    @TableField(value = "positionNo")
    private Integer positionNo;
    @TableField(value = "available")
    private boolean available;
}

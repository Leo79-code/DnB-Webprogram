package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.administratorsidesoftware.common.GoodsType;
import lombok.Data;

@Data
@TableName(value = "goods")
public class Goods {
    @TableId(value = "goodsId")
    private Integer goodsId;
    @TableField(value = "Color")
    private GoodsType color;
    @TableField(value = "positionNo")
    private Integer positionNo;
}

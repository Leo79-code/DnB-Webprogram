package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.service.GoodsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;



@Data
@TableName(value = "goods")
public class Goods {

    @TableId(value = "goodsId",type = IdType.AUTO)
    private Integer goodsId;
    @TableField(value = "color")
    private GoodsType color;
    @TableField(value = "positionNo")
    private Integer positionNo;
    @TableField(value = "available")
    private boolean available;


}

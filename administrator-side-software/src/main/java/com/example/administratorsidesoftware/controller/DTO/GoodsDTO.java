package com.example.administratorsidesoftware.controller.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.administratorsidesoftware.common.GoodsType;
import lombok.Data;

@Data
public class GoodsDTO {
    private Integer goodsId;
    private GoodsType color;
    private Integer positionNo;
    private boolean available;
}

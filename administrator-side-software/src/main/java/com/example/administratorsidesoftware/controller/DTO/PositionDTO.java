package com.example.administratorsidesoftware.controller.DTO;

import com.example.administratorsidesoftware.common.GoodsType;
import lombok.Data;

@Data
public class PositionDTO {
    private Integer positionNo;
    private boolean available;
    private Integer warehouseNo;
    private Integer goodsId;
    private GoodsType color;
    private String strAvailable;
}

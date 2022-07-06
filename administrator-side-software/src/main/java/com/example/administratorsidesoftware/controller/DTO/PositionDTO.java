package com.example.administratorsidesoftware.controller.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PositionDTO {
    private Integer positionNo;
    private boolean available;
}

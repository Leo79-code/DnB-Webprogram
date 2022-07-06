package com.example.administratorsidesoftware.controller.DTO;

import lombok.Data;

@Data
public class WorkerDTO {
    private Integer workerId;
    private Integer managerId;
    private Integer warehouseNo;
    private boolean isEmployed;
}

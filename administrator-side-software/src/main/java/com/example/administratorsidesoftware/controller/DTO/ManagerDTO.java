package com.example.administratorsidesoftware.controller.DTO;

import lombok.Data;

@Data
public class ManagerDTO {
    public String managerPassword;
    public Integer managerId;
    public String managerName;
    // hCaptcha verification token
    public String token;
}

package com.example.administratorsidesoftware.controller.DTO;

import lombok.Data;

@Data
public class ManagerDTO {
    public String managerPassword;
    public Integer managerId;
    public String managerName;

    public String managerEmail;

    public String managerMobile;

    // hCaptcha verification token
    public String token;
}

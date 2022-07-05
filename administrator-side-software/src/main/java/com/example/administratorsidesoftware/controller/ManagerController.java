package com.example.administratorsidesoftware.controller;

import cn.hutool.core.util.StrUtil;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manager/")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("/login")
    public boolean login(@RequestBody ManagerDTO managerDTO){
        String username = managerDTO.getManagerName();
        String password = managerDTO.getManagerPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return false;
        }
        return managerService.login(managerDTO);
    }

    @GetMapping("/")
    public List<Manager>  index() {return managerService.list();}
}

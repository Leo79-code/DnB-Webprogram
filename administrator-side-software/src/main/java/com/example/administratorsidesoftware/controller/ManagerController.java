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

    /**
     * Login interface, provides login function to front-end
     * @param managerDTO manager data transfer object
     * @return true if the user is valid, false if the user isn't valid.
     */
    @PostMapping("/login")
    public boolean login(@RequestBody ManagerDTO managerDTO){
        String username = managerDTO.getManagerName();
        String password = managerDTO.getManagerPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return false;
        }
        return managerService.login(managerDTO);
    }

    //列出所有manager
    @GetMapping("/")
    public List<Manager>  index() {return managerService.list();}
}

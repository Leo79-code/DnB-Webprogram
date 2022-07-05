package com.example.administratorsidesoftware.controller;

import cn.hutool.core.util.StrUtil;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("manager/")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    /**
     * Login interface, provides login function to front-end
     *
     * @param managerDTO manager data transfer object
     * @return true if the user is valid, false if the user isn't valid.
     */
    @PostMapping("/login")
    public Result login(@RequestBody ManagerDTO managerDTO, HttpSession session) {
        Integer id = managerDTO.getManagerId();
        String password = managerDTO.getManagerPassword();
        if (id == null || StrUtil.isBlank(password)) {
            return Result.error("The input is empty");
        }
        Manager login = managerService.login(managerDTO);
        if (login != null) {
            session.setAttribute("managerId", login.getManagerId());
            return Result.success(login);
        } else {
            return Result.error("Input error");
        }
    }
}

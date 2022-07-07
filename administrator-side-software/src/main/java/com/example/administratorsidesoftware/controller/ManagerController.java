package com.example.administratorsidesoftware.controller;

import cn.hutool.core.util.StrUtil;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;

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
    public Result login(@RequestBody ManagerDTO managerDTO, HttpServletRequest res) {
        Integer id = managerDTO.getManagerId();
        String password = managerDTO.getManagerPassword();
        if (id == null || StrUtil.isBlank(password)) {
            return Result.error("The input is empty");
        }
        Manager login = managerService.login(managerDTO);
        if (login != null) {
            res.getSession().setAttribute("managerId", login.getManagerId());
            return Result.success(res.getSession().getId());
        } else {
            return Result.error("Input error");
        }
    }

    //Session test
//    @GetMapping("/")
//    public String setSession(HttpCookie cookie) {
//        cookie.setValue("");
//        return "set session";
//    }
//
//    @GetMapping("/session")
//    public String getSession(HttpServletRequest res) {
//        return (String) res.getSession().getAttribute("session");
//    }
}

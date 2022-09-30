package com.example.administratorsidesoftware.controller;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import cn.hutool.core.util.StrUtil;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.common.State;
import com.example.administratorsidesoftware.controller.DTO.CaptchaDTO;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



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
    public Result login(@RequestBody ManagerDTO managerDTO) {
        String token = managerDTO.getToken();
        String auth_url = "https://hcaptcha.com/siteverify";
        String secret = "0x2598A9B7D33c4cd7AafDdCf94d651b4b0f90AFf1";
        MultiValueMap params = new LinkedMultiValueMap<>();
        params.add("secret", secret);
        params.add("response", token);

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod conn_method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> req_entity = new HttpEntity<>(params, headers);
        ResponseEntity<CaptchaDTO> res_entity = client.exchange(
                auth_url,
                conn_method,
                req_entity,
                CaptchaDTO.class
        );
        CaptchaDTO ans = res_entity.getBody();
        Boolean is_verified_captcha = ans.success;
        System.out.println("Captcha verification status: " + is_verified_captcha.toString());

        Integer id = managerDTO.getManagerId();
        String password = managerDTO.getManagerPassword();
        if (id == null || StrUtil.isBlank(password)) {
            return Result.error("The input is empty");
        }
        Manager login = managerService.login(managerDTO);
        if (login != null && is_verified_captcha) {
            return Result.success(login);
        } else {
            return Result.error("Input error");
        }
    }

    /**
     * 修改manager信息（逻辑上不能改managerId，前端将默认Id传进DTO中）
     * @param managerDTO 需要更改的信息+managerId
     * @return 成功返回SUCCESS，失败返回BUSINESS_ERROR
     */
    @PostMapping("/change")
    public Result change(@RequestBody ManagerDTO managerDTO) {
        boolean result = managerService.updateManagerById(managerDTO);
        if(result){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/regist")
    public Result regist(@RequestBody ManagerDTO managerDTO){
        boolean result = managerService.managerRegist(managerDTO);
        if(result){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, String> requestParams) {
        ManagerDTO managerDTO = new ManagerDTO();

        Integer id = ThreadLocalRandom.current().nextInt(100000, 100000001);
        managerDTO.setManagerId(id);
        managerDTO.setManagerMobile("");
        managerDTO.setManagerEmail(requestParams.get("email"));
        managerDTO.setManagerName(requestParams.get("name"));
        managerDTO.setManagerPassword(requestParams.get("password"));
        boolean result = managerService.managerRegist(managerDTO);
        if (result) {
            Result res = new Result();
            res.setState(State.SUCCESS);
            res.setMsg(id.toString());
            res.setData(null);
            return res;
        } else {
            return Result.error("Register failed!");
        }
    }
}

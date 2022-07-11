package com.example.administratorsidesoftware.controller;

import cn.hutool.core.util.StrUtil;
import com.example.administratorsidesoftware.common.Result;
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
     * @param managerDTO manager data transfer object，需要传入managerId，managerPassword，token
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
     * 修改manager信息
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

    /**
     * 注册manager
     * @param managerDTO 需要传入managerName，managerPassword
     * @return 成功返回SUCCESS，失败返回BUSINESS_ERROR
     */
    @PostMapping("/regist")
    public Result regist(@RequestBody ManagerDTO managerDTO){
        boolean result = managerService.managerRegist(managerDTO);
        if(result){
            return Result.success();
        }else {
            return Result.error();
        }
    }
}

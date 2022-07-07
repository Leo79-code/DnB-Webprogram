package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.controller.DTO.ManagerDTO;
import com.example.administratorsidesoftware.entity.Manager;
import com.example.administratorsidesoftware.mapper.ManagerMapper;
import org.springframework.stereotype.Service;


@Service
public class ManagerService extends ServiceImpl<ManagerMapper, Manager> {
    public Manager login(ManagerDTO managerDTO) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerDTO.getManagerId());
        queryWrapper.eq("managerPassword", managerDTO.getManagerPassword());
        return getOne(queryWrapper);
    }

    public boolean updateManagerById(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        BeanUtil.copyProperties(managerDTO,manager);
        return updateById(manager);
    }

    public boolean managerRegist(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        BeanUtil.copyProperties(managerDTO,manager);
        return save(manager);
    }
}

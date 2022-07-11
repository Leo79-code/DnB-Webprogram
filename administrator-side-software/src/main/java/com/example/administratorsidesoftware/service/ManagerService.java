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

    /**
     * manager登录功能
     * @param managerDTO 传入managerId，managerPassword
     * @return 登录成功返回该manager对象的信息
     */
    public Manager login(ManagerDTO managerDTO) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerDTO.getManagerId());
        queryWrapper.eq("managerPassword", managerDTO.getManagerPassword());
        return getOne(queryWrapper);
    }

    /**
     * 更新manager的信息
     * @param managerDTO 传入managerId，和需要更细的信息
     * @return 更新成功返回true，否则返回false
     */
    public boolean updateManagerById(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        BeanUtil.copyProperties(managerDTO,manager);
        return updateById(manager);
    }

    /**
     * 注册manager
     * @param managerDTO 传入managerName，managerPassword
     * @return 注册成功返回true，否则返回false
     */
    public boolean managerRegist(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        BeanUtil.copyProperties(managerDTO,manager);
        return save(manager);
    }
}

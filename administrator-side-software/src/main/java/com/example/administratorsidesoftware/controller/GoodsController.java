package com.example.administratorsidesoftware.controller;

import com.example.administratorsidesoftware.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods/")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


}

package com.example.administratorsidesoftware.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods/")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 通过id删除goods，将isEmplyed置为false
     * @param id 要删除的goods的id
     * @return 成功返回success状态，失败返回business_error状态
     */
    @DeleteMapping("/{id}")
    public Result deleteGoods(@PathVariable Integer id){
        boolean result = goodsService.deleteGoodsById(id);
        if(result){
            return Result.success(true);
        }else {
            return Result.error("user error",false);
        }
    }

    /**
     * 改变goods位置, 同时更新position
     * @param goodsDTO goods data transfer object
     * @return
     */
    @PostMapping("/change/position")
    public Result updateGoodsPosition(@RequestBody GoodsDTO goodsDTO){
        boolean result = goodsService.updatePosition(goodsDTO);
        if(result) {
            return Result.success(true);
        } else {
            return Result.error("user error",false);
        }
    }

    @PostMapping("/change/color")
    public Result updateGoodsColor(@RequestBody GoodsDTO goodsDTO){
        boolean result = goodsService.updateColor(goodsDTO);
        if(result){
            return Result.success(true);
        }else {
            return Result.error("user error", false);
        }
    }


    /**
     * Paging shows all goods in a warehouse
     *
     * @param pageNum     The sequence number of the page displayed
     * @param pageSize    Maximum number of items that can be displayed per page
     * @param warehouseNo The number of the warehouse being queried
     * @return The information contained in the current page that should be displayed
     */
    @GetMapping("/warehouse/{warehouseNo}/list/page")
    public Result listGoodsPageByWarehouse(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @PathVariable Integer warehouseNo) {
        IPage<Goods> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warehouseNo", warehouseNo);
        queryWrapper.eq("available",true);
        return Result.success(goodsService.page(page,queryWrapper));
    }

    @GetMapping("/type/list/page")
    public Result listGoodsPageByType(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam GoodsType type) {
        IPage<Goods> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("color", type);
        queryWrapper.eq("available",true);
        return Result.success(goodsService.page(page,queryWrapper));
    }

    @PostMapping("/add")
    public Result addGoods(@RequestBody GoodsDTO goodsDTO){
        boolean result = goodsService.addGoods(goodsDTO);
        if(result){
            return Result.success(true);
        }else {
            return Result.error("user error",false);
        }
    }
}

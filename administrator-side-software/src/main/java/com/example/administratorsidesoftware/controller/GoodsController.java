package com.example.administratorsidesoftware.controller;

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
     * 通过id删除goods，将available置为false
     *
     * @param id 要删除的goods的id
     * @return 成功返回success状态，失败返回business_error状态
     */
    @DeleteMapping("/{id}")
    public Result deleteGoods(@PathVariable Integer id) {
        boolean result = goodsService.deleteGoodsById(id);
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("user error", false);
        }
    }

    /**
     * 只改变goods位置, 会同步更新position
     *
     * @param goodsDTO goods data transfer object
     * @return 更改成功返回success，失败返回user_error
     */
    @PostMapping("/change/position")
    public Result updateGoodsPosition(@RequestBody GoodsDTO goodsDTO) {
        boolean result = goodsService.updatePosition(goodsDTO, goodsDTO.getManagerId());
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("user error", false);
        }
    }

    /**
     * 改变货物的状态信息，目前只有颜色状态，若后期扩充字段在这里更改
     *
     * @param goodsDTO 需要更改的信息封装到goodsDTO中
     * @return 更改成功返回success，更改失败返回user_error
     */
    @PostMapping("/change/color")
    public Result updateGoodsColor(@RequestBody GoodsDTO goodsDTO) {
        boolean result = goodsService.updateColor(goodsDTO);
        if (result) {
            return Result.success(true);
        } else {
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
        return Result.success(goodsService.findGoodsPageByWarehouse(page, warehouseNo));
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

    /**
     * 通过color/goodsId分页查找货物，只能显示该manager管理的
     * warehouse 中可获得的货物, color与goodsId可以不输入，若不输入则显示该manager拥有的所有货物
     *
     * @param pageNum  第几页
     * @param pageSize 页容量
     * @param color    种类，可不输入
     * @param goodsId  可不输入，若输入则为精确查找
     * @param managerId
     * @return 无论查找出来几项都会返回success
     */
    @GetMapping("/find/page")
    public Result findGoodsPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(required = false) GoodsType color,
                                @RequestParam(required = false) Integer goodsId,
                                @RequestParam Integer managerId) {
        IPage<Goods> page = new Page<>(pageNum, pageSize);
        return Result.success(goodsService.findGoodsPage(page, goodsId, color, managerId));
    }
}

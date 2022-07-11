package com.example.administratorsidesoftware.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.controller.DTO.GoodsDTO;
import com.example.administratorsidesoftware.entity.Goods;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.mapper.GoodsMapper;
import com.example.administratorsidesoftware.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
    @Autowired
    private PositionService positionService;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private PositionMapper positionMapper;

    /**
     * 通过goodsId删除goods
     * @param id 需要删除的goods的id
     * @return 成功删除返回true，失败返回false
     */
    public boolean deleteGoodsById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.isAvailable()) {
            goods.setAvailable(false);
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtil.copyProperties(goods, goodsDTO);
            return positionService.takeout(goodsDTO) && updateById(goods);
        } else {
            return false;
        }
    }

    /**
     * 更新goods的position
     * @param goodsDTO 需要更新到的positionNo，需要更新的goodsId，操作用户的managerId
     * @return 成功返回true，失败返回false
     */
    public boolean updatePosition(GoodsDTO goodsDTO) {
        Goods goods = new Goods();
        BeanUtil.copyProperties(goodsDTO, goods);
        goods.setAvailable(true);
        Goods goods1 = goodsMapper.selectById(goodsDTO.getGoodsId());
        Position position = positionMapper.selectById(goods1.getPositionNo());
        if (positionService.takeout(goodsDTO)) {
            if (positionService.putin(goodsDTO)) {
                return updateById(goods);
            } else {
                goodsDTO.setPositionNo(position.getPositionNo());
                positionService.putin(goodsDTO);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 更新goods的color
     * @param goodsDTO goodsId，color
     * @return 成功返回true，失败返回false
     */
    public boolean updateColor(GoodsDTO goodsDTO) {
        Goods goods = goodsMapper.selectById(goodsDTO.getGoodsId());
        if (goods.isAvailable()) {
            Goods goods1 = new Goods();
            BeanUtil.copyProperties(goodsDTO, goods1);
            return updateById(goods1);
        } else {
            return false;
        }
    }

    /**
     * 添加goods
     * @param goodsDTO goodsId，要添加到的positionNo，执行操作的manager的Id，goods的color
     * @return 成功返回true，失败返回false
     */
    public boolean addGoods(GoodsDTO goodsDTO) {
        Goods goods = new Goods();
        BeanUtil.copyProperties(goodsDTO, goods);
        goods.setAvailable(true);
        boolean result = goodsMapper.insert(goods) == 1;
        BeanUtil.copyProperties(goods, goodsDTO);
        result = result && positionService.putin(goodsDTO);
        return result;
    }

    /**
     * 分页查询warehouse中的goods
     * @param page 查询结果会储存在page中，page中储存有pageNum和pageSize信息
     * @param warehouseNo 要查询的warehouseNo
     * @return 查询结果page
     */
    public IPage<Goods> findGoodsPageByWarehouse(IPage<Goods> page, Integer warehouseNo) {
        return goodsMapper.findGoodsPageByWarehouse(page, warehouseNo);
    }

    /**
     * 分页查询manager的goods
     * @param page 查询结果会储存在page中，page中储存有pageNum和pageSize信息
     * @param goodsId 需要搜索的goodsId，可不输入
     * @param color 需要搜索的color，可不输入
     * @param managerId 执行搜索的user的managerId，必须输入
     * @return 查询结果page
     */
    public IPage<Goods> findGoodsPage(IPage<Goods> page, Integer goodsId, GoodsType color, Integer managerId) {
        return goodsMapper.findGoodsPage(page, goodsId, color, managerId);
    }
}

package com.example.administratorsidesoftware.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.entity.Position;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.service.PositionService;
import com.example.administratorsidesoftware.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("echarts/")
public class EchartsController {
    @Resource
    private RecordService recordService;

    @Resource
    private PositionService positionService;

//    //TODO 每个货架的利用率（本年度起算）


    /**
     * 饼状图，显示warehouse中被占用的position和没被占用的position的占比
     * @param warehouseNo 要查找的warehouse
     * @return 按顺序分别是未被占用的，被占用的
     */
    @GetMapping("/position/{warehouseNo}/occupy")
    public Result getPositionOccupyEcharts(@PathVariable Integer warehouseNo){
        List<Position> positions = positionService.listPositionByWarehouse(warehouseNo);
        int available = 0;
        int unavailable = 0;
        for (Position position : positions) {
            if(position.isAvailable()){
                available++;
            }else {
                unavailable++;
            }
        }
        return Result.success(CollUtil.newArrayList(available,unavailable));
    }


    /**
     * 分析某个仓库每个季度的进货量
     *
     * @param warehouseNo 仓库ID
     * @return 按顺序分别是第1，2，3，4季度进货量
     */
    @GetMapping("/warehouse/{warehouseNo}/deposit")
    public Result getWarehouseDepositEcharts(@PathVariable Integer warehouseNo) {
        List<Record> records = recordService.searchRecordByWarehouse(warehouseNo);
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Record record : records) {
            Date depositTime = record.getDepositTime();
            Quarter quarter = DateUtil.quarterEnum(depositTime);
            switch (quarter) {
                case Q1:
                    q1++;
                    break;
                case Q2:
                    q2++;
                    break;
                case Q3:
                    q3++;
                    break;
                case Q4:
                    q4++;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    /**
     * 分析某仓库四个季度的出货量
     *
     * @param warehouseNo 仓库ID
     * @return arraylist，按顺序分别是第1，2，3，4季度的出货量
     */
    @GetMapping("/warehouse/{warehouseNo}/takeout")
    public Result getWarehouseTakeoutEcharts(@PathVariable Integer warehouseNo) {
        List<Record> records = recordService.searchRecordByWarehouse(warehouseNo);
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Record record : records) {
            Date takeoutTime = record.getTakeoutTime();
            if (takeoutTime != null) {
                Quarter quarter = DateUtil.quarterEnum(takeoutTime);
                switch (quarter) {
                    case Q1:
                        q1++;
                        break;
                    case Q2:
                        q2++;
                        break;
                    case Q3:
                        q3++;
                        break;
                    case Q4:
                        q4++;
                        break;
                    default:
                        break;
                }
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

}

package com.example.administratorsidesoftware.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.administratorsidesoftware.common.Result;
import com.example.administratorsidesoftware.entity.Record;
import com.example.administratorsidesoftware.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("echarts/")
public class EchartsController {
    @Resource
    private RecordService recordService;

//    //TODO 每个货架的利用率（本年度起算）


    /**
     * 分析某个仓库每个季度的进货量
     *
     * @param warehouseId 仓库ID
     * @return 按顺序分别是第1，2，3，4季度进货量
     */
    @GetMapping("/warehouse/{warehouseId}/deposit")
    public Result getWarehouseDepositEcharts(@PathVariable Integer warehouseId) {
        List<Record> records = recordService.searchRecordByWarehouse(warehouseId);
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
     * @param warehouseId 仓库ID
     * @return arraylist，按顺序分别是第1，2，3，4季度的出货量
     */
    @GetMapping("/warehouse/{warehouseId}/takeout")
    public Result getWarehouseTakeoutEcharts(@PathVariable Integer warehouseId) {
        List<Record> records = recordService.searchRecordByWarehouse(warehouseId);
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

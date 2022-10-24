package com.example.administratorsidesoftware.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.administratorsidesoftware.common.GoodsType;
import com.example.administratorsidesoftware.mapper.RecordMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName(value = "record")
public class Record {

    @TableId(value = "recordId", type = IdType.AUTO)
    private Integer recordId;
    @TableField(value = "goodsId")
    private Integer goodsId;
    @TableField(value = "positionNo")
    private Integer positionNo;
    @TableField(value = "takeoutTime")
    private Timestamp takeoutTime;
    @TableField(value = "depositTime")
    private Timestamp depositTime;
    @TableField(value = "warehouseNo")
    private Integer warehouseNo;
    @TableField(value = "color")
    private GoodsType color;

    public Record(Integer positionNo, Integer goodsId, GoodsType color, Integer warehouseNo) {
        this.setGoodsId(goodsId);
        this.setPositionNo(positionNo);
        this.setColor(color);
        this.setWarehouseNo(warehouseNo);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.setDepositTime(timestamp);
    }

    public void takeout() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.setTakeoutTime(timestamp);
    }

}

package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName DCS_boiler_monitoring
 */
@TableName(value ="DCS_boiler_monitoring")
@Data
public class DcsBoilerMonitoring2 implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 锅炉出口温度
     */
    private String boilerOutletTemperature;

    /**
     * 锅炉出口压力
     */
    private String boilerOutletPressure;

    /**
     * 锅炉出口焓
     */
    private String enthalpyAtBoilerOutlet;

    /**
     * 锅炉右侧省煤器出口烟气含氧量
     */
    private String theRightSideOfTheBoiler;

    /**
     * 锅炉左侧省煤器出口烟气含氧量
     */
    private String theLeftSideOfTheBoiler;

    /**
     * 锅炉储水罐水位
     */
    private String waterLevelInBoilerStorageTank;

    /**
     * 锅炉入口风量
     */
    private String airFlowRateAtBoilerInlet;

    /**
     * 锅炉入口风压力
     */
    private String airPressureAtBoilerInlet;

    /**
     * 锅炉燃烧平均氧含量
     */
    private String averageOxygenContentBoilerCombustion;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
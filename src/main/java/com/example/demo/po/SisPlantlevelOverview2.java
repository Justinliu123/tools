package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName SIS_plantlevel_overview
 */
@TableName(value ="SIS_plantlevel_overview_2")
@Data
public class SisPlantlevelOverview2 implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 负荷
     */
    private String loads;

    /**
     * 供电煤耗
     */
    private String powerSupplyCoalConsumption;

    /**
     * 发电煤耗
     */
    private String coalConsumptionForPowerGeneration;

    /**
     * 厂用电率
     */
    private String auxiliaryPowerRatio;

    /**
     * 锅炉效率
     */
    private String boilerEfficiency;

    /**
     * 气机热耗
     */
    private String steamTurbineHeatRate;

    /**
     * 汽耗率
     */
    private String steamConsumptionRate;

    /**
     * 汽机效率
     */
    private String steamTurbineEfficiency;

    /**
     * 机组效率
     */
    private String generatingUnitEfficiency;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
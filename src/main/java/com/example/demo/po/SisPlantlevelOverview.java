package com.example.demo.po;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName SIS_plantlevel_overview
 */
@TableName(value ="SIS_plantlevel_overview")
@Data
public class SisPlantlevelOverview implements Serializable {
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


    public void setEmptyFieldsToZero() {
        this.loads = setToZeroIfEmpty(this.loads);
        this.powerSupplyCoalConsumption = setToZeroIfEmpty(this.powerSupplyCoalConsumption);
        this.coalConsumptionForPowerGeneration = setToZeroIfEmpty(this.coalConsumptionForPowerGeneration);
        this.auxiliaryPowerRatio = setToZeroIfEmpty(this.auxiliaryPowerRatio);
        this.boilerEfficiency = setToZeroIfEmpty(this.boilerEfficiency);
        this.steamTurbineHeatRate = setToZeroIfEmpty(this.steamTurbineHeatRate);
        this.steamConsumptionRate = setToZeroIfEmpty(this.steamConsumptionRate);
        this.steamTurbineEfficiency = setToZeroIfEmpty(this.steamTurbineEfficiency);
        this.generatingUnitEfficiency = setToZeroIfEmpty(this.generatingUnitEfficiency);
    }

    private String setToZeroIfEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        return value;
    }
}
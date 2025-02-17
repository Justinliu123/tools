package com.example.demo.po;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName SIS_turbine_system
 */
@TableName(value ="SIS_turbine_system")
@Data
public class SisTurbineSystem implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 压力控制器团充注压力
     */
    private String pressureControllerGroupChargingPressure;

    /**
     * 机组增负载效率
     */
    private String unitLoadIncreaseRate;

    /**
     * 机组减负载效率
     */
    private String unitLoadDecreaseRate;

    /**
     * 运行调节器状态值
     */
    private String operatingRegulatorStatusValue;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void setEmptyFieldsToZero() {
        this.pressureControllerGroupChargingPressure = setToZeroIfEmpty(this.pressureControllerGroupChargingPressure);
        this.unitLoadIncreaseRate = setToZeroIfEmpty(this.unitLoadIncreaseRate);
        this.unitLoadDecreaseRate = setToZeroIfEmpty(this.unitLoadDecreaseRate);
        this.operatingRegulatorStatusValue = setToZeroIfEmpty(this.operatingRegulatorStatusValue);
    }

    private String setToZeroIfEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        return value;
    }
}
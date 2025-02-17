package com.example.demo.po;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName DCS_turbine_data
 */
@TableName(value ="DCS_turbine_data")
@Data
public class DcsTurbineData implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 汽轮机效率
     */
    private String steamTurbineEfficiency;

    /**
     * 发电机_d_轴电抗
     */
    private String generatorDAxisReactance;

    /**
     * 发电机_q_轴电抗
     */
    private String generatorQAxisReactance;

    /**
     * 发电机电阻
     */
    private String generatorResistance;

    /**
     * 发电机阻尼系数
     */
    private String generatorDampingCoefficient;

    /**
     * 发电机转动惯量
     */
    private String generatorMomentOfInertia;

    /**
     * 发电机_d_轴参考电压
     */
    private String generatorDAxisReferenceVoltage;

    /**
     * 发电机_q_轴参考电压
     */
    private String generatorQAxisReferenceVoltage;

    /**
     * 汽轮机应力值
     */
    private String steamTurbineStressValue;

    /**
     * 汽轮机进汽温度
     */
    private String steamTurbineInletSteamTemperature;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void setEmptyFieldsToZero() {
        this.steamTurbineEfficiency = setToZeroIfEmpty(this.steamTurbineEfficiency);
        this.generatorDAxisReactance = setToZeroIfEmpty(this.generatorDAxisReactance);
        this.generatorQAxisReactance = setToZeroIfEmpty(this.generatorQAxisReactance);
        this.generatorResistance = setToZeroIfEmpty(this.generatorResistance);
        this.generatorDampingCoefficient = setToZeroIfEmpty(this.generatorDampingCoefficient);
        this.generatorMomentOfInertia = setToZeroIfEmpty(this.generatorMomentOfInertia);
        this.generatorDAxisReferenceVoltage = setToZeroIfEmpty(this.generatorDAxisReferenceVoltage);
        this.generatorQAxisReferenceVoltage = setToZeroIfEmpty(this.generatorQAxisReferenceVoltage);
        this.steamTurbineStressValue = setToZeroIfEmpty(this.steamTurbineStressValue);
        this.steamTurbineInletSteamTemperature = setToZeroIfEmpty(this.steamTurbineInletSteamTemperature);
    }

    private String setToZeroIfEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        return value;
    }
}
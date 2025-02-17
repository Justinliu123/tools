package com.example.demo.po;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName SIS_upgrade_voltage_transformer_data
 */
@TableName(value ="SIS_upgrade_voltage_transformer_data")
@Data
public class SisUpgradeVoltageTransformerData implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 升压变压器入口电压
     */
    private String boostTransformerInputVoltage;

    /**
     * 升压变压器出口电压
     */
    private String boostTransformerOutputVoltage;

    /**
     * 升压变压器输出电流
     */
    private String boostTransformerOutputCurrent;

    /**
     * 升压变压器输出功率
     */
    private String boostTransformerOutputPower;

    /**
     * 输电线输输电压
     */
    private String transmissionLineVoltage;

    /**
     * 输电线电流
     */
    private String transmissionLineCurrent;

    /**
     * 输电线功率
     */
    private String transmissionLinePower;

    /**
     * 降压变压器入口电压
     */
    private String stepDownTransformerInputVoltage;

    /**
     * 降压变压器出口电压
     */
    private String stepDownTransformerOutputVoltage;

    /**
     * 降压变压器输出电流_
     */
    private String stepDownTransformerOutputCurrent;

    /**
     * 降压变压器输出功率
     */
    private String stepDownTransformerOutputPower;

    /**
     * 输电线输送效率
     */
    private String transmissionLineEfficiency;

    /**
     * 输电线连线状态
     */
    private String transmissionLineConnectionStatus;

    /**
     * 输电线运行温度
     */
    private String transmissionLineOperatingTemperature;

    /**
     * 输电线连线健康状态
     */
    private String transmissionLineConnectionHealthStatus;

    /**
     * _网络输出功率分配
     */
    private String networkOutputPowerAllocation;

    /**
     * 状态网络连接点功率平衡状态
     */
    private String powerAtNetworkConnectionPoints;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void setEmptyFieldsToZero() {
        this.boostTransformerInputVoltage = setToZeroIfEmpty(this.boostTransformerInputVoltage);
        this.boostTransformerOutputVoltage = setToZeroIfEmpty(this.boostTransformerOutputVoltage);
        this.boostTransformerOutputCurrent = setToZeroIfEmpty(this.boostTransformerOutputCurrent);
        this.boostTransformerOutputPower = setToZeroIfEmpty(this.boostTransformerOutputPower);
        this.transmissionLineVoltage = setToZeroIfEmpty(this.transmissionLineVoltage);
        this.transmissionLineCurrent = setToZeroIfEmpty(this.transmissionLineCurrent);
        this.transmissionLinePower = setToZeroIfEmpty(this.transmissionLinePower);
        this.stepDownTransformerInputVoltage = setToZeroIfEmpty(this.stepDownTransformerInputVoltage);
        this.stepDownTransformerOutputVoltage = setToZeroIfEmpty(this.stepDownTransformerOutputVoltage);
        this.stepDownTransformerOutputCurrent = setToZeroIfEmpty(this.stepDownTransformerOutputCurrent);
        this.stepDownTransformerOutputPower = setToZeroIfEmpty(this.stepDownTransformerOutputPower);
        this.transmissionLineEfficiency = setToZeroIfEmpty(this.transmissionLineEfficiency);
        this.transmissionLineConnectionStatus = setToZeroIfEmpty(this.transmissionLineConnectionStatus);
        this.transmissionLineOperatingTemperature = setToZeroIfEmpty(this.transmissionLineOperatingTemperature);
        this.transmissionLineConnectionHealthStatus = setToZeroIfEmpty(this.transmissionLineConnectionHealthStatus);
        this.networkOutputPowerAllocation = setToZeroIfEmpty(this.networkOutputPowerAllocation);
        this.powerAtNetworkConnectionPoints = setToZeroIfEmpty(this.powerAtNetworkConnectionPoints);
    }

    private String setToZeroIfEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        return value;
    }

}
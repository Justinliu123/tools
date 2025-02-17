package com.example.demo.po;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName DCS_monitoring_of_auxiliary_turbines
 */
@TableName(value ="DCS_monitoring_of_auxiliary_turbines")
@Data
public class DcsMonitoringOfAuxiliaryTurbines implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 泵效率
     */
    private String pumpEfficiency;

    /**
     * 泵入口压力
     */
    private String pumpInletPressure;

    /**
     * 泵出口压力
     */
    private String pumpOutletPressure;

    /**
     * 机组scr反应器2a脱硝后烟气nox含量
     */
    private String noxConcentrationInFlueGas;

    /**
     * 机组scr反应器2a脱硝后烟气o2含量
     */
    private String o2ConcentrationInFlueGas;

    /**
     * 机组scr反应器2a脱硝后烟气nh₃含量
     */
    private String nh3ConcentrationInFlueGas;

    /**
     * scr反应器的压力保持状况
     */
    private String pressureHoldingStatusOfTheScrReactor;

    /**
     * 送风机电动机电流
     */
    private String forcedDraftFanMotorCurrent;

    /**
     * 送风机电动机功率
     */
    private String forcedDraftFanMotorPower;

    /**
     * 送风机风压压力
     */
    private String forcedDraftFanAirPressure;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void setEmptyFieldsToZero() {
        this.pumpEfficiency = setToZeroIfEmpty(this.pumpEfficiency);
        this.pumpInletPressure = setToZeroIfEmpty(this.pumpInletPressure);
        this.pumpOutletPressure = setToZeroIfEmpty(this.pumpOutletPressure);
        this.noxConcentrationInFlueGas = setToZeroIfEmpty(this.noxConcentrationInFlueGas);
        this.o2ConcentrationInFlueGas = setToZeroIfEmpty(this.o2ConcentrationInFlueGas);
        this.nh3ConcentrationInFlueGas = setToZeroIfEmpty(this.nh3ConcentrationInFlueGas);
        this.pressureHoldingStatusOfTheScrReactor = setToZeroIfEmpty(this.pressureHoldingStatusOfTheScrReactor);
        this.forcedDraftFanMotorCurrent = setToZeroIfEmpty(this.forcedDraftFanMotorCurrent);
        this.forcedDraftFanMotorPower = setToZeroIfEmpty(this.forcedDraftFanMotorPower);
        this.forcedDraftFanAirPressure = setToZeroIfEmpty(this.forcedDraftFanAirPressure);
    }

    private String setToZeroIfEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        return value;
    }
}
package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName DCS_turbine_data
 */
@TableName(value ="DCS_turbine_data")
@Data
public class DcsTurbineData2 implements Serializable {
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
}
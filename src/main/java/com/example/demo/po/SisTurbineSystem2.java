package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName SIS_turbine_system
 */
@TableName(value ="SIS_turbine_system_2")
@Data
public class SisTurbineSystem2 implements Serializable {
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
}
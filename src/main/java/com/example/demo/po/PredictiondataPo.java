package com.example.demo.po;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName predictiondata
 */
@TableName(value ="predictiondata")
@Data
public class PredictiondataPo implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 短期预测功率
     */
    @JSONField(name = "shortTermPredictedPower")
    private String shorttermpredictedpower;

    /**
     * 超短期预测功率
     */
    @JSONField(name = "ultraShortTermPredictedPower")
    private String ultrashorttermpredictedpower;

    /**
     * 实际风速
     */
    @JSONField(name = "actualWindSpeed")
    private String actualwindspeed;

    /**
     * 短期预测风速
     */
    @JSONField(name = "shortTermPredictedWindSpeed")
    private String shorttermpredictedwindspeed;

    /**
     * 超短期预测风速
     */
    @JSONField(name = "ultraShortTermPredictedWindSpeed")
    private String ultrashorttermpredictedwindspeed;

    /**
     * 测风塔风速
     */
    @JSONField(name = "anemometerWindSpeed")
    private String anemometerwindspeed;

    /**
     * 创建人
     */
    private String createusercode;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新人
     */
    private String modifyusercode;

    /**
     * 更新时间
     */
    private Date modifytime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
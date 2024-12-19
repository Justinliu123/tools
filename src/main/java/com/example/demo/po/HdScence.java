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
 * 整体工况，用在“电场工况仿真”整体工况状态界面（参考）   的 第二部分
 * @TableName scence
 */
@TableName(value ="hd_scence")
@Data
public class HdScence implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 装机容量
     */
    private String capacity;

    /**
     * 发电负荷
     */
    private String fload;

    /**
     * 负荷
     */
    private String loadx;

    /**
     * 正常发电
     */
    @JSONField(name = "normalGeneratingNumber")
    private String normalgeneratingnumber;

    /**
     * 带病发电
     */
    @JSONField(name = "generatingNumber")
    private String generatingnumber;

    /**
     * 停机次数
     */
    @JSONField(name = "stopNumber")
    private String stopnumber;

    /**
     * 通讯状态
     */
    private String communicationstatus;

    /**
     * 风机类型
     */
    private String windpattern;

    /**
     * 风轮直径
     */
    @JSONField(name = "rotorDiameter")
    private String rotordiameter;

    /**
     * 升压站状态
     */
    @JSONField(name = "boosterStationStatus")
    private String boosterstationstatus;

    /**
     * 电缆状态
     */
    private String cable;

    /**
     * 风机塔状态
     */
    @JSONField(name = "fanTower")
    private String fantower;

    /**
     * 风机机组状态
     */
    @JSONField(name = "fanUnit")
    private String fanunit;

    /**
     * 功率
     */
    private String power;

    /**
     * 环境湿度
     */
    @JSONField(name = "ambientHumidity")
    private String ambienthumidity;

    /**
     * 风速
     */
    private String windSpeed;

    /**
     * 空气密度
     */
    @JSONField(name = "airDensity")
    private String airdensity;

    /**
     * 气压
     */
    @JSONField(name = "airPressure")
    private String airpressure;

    /**
     * 风向
     */
    private String winddirection;

    /**
     * 风向角度
     */
    @JSONField(name = "windDirectionAngle")
    private String winddirectionangle;

    /**
     * 装机容量2
     */
    @JSONField(name = "installedCapacity")
    private String installedcapacity;

    /**
     * 发电电量
     */
    @JSONField(name = "generatingCapacity")
    private String generatingcapacity;

    /**
     * 有功功率
     */
    @JSONField(name = "activePower")
    private String activepower;

    /**
     * 无功功率
     */
    @JSONField(name = "reactivePower")
    private String reactivepower;

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
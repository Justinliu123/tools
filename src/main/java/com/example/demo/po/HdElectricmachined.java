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
 * @TableName electricmachined
 */
@TableName(value ="hd_electricmachined")
@Data
public class HdElectricmachined implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 风机部位编号
     */
    @JSONField(name = "equipmentPartsCode")
    private String equipmentpartscode;

    /**
     * 点位英文变量名
     */
    @JSONField(name = "equipMappingEN")
    private String equipmappingen;

    /**
     * 点位仿真数据
     */
    @JSONField(name = "simulationData")
    private String simulationdata;

    /**
     * 历史数据
     */
    private String historydata;

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
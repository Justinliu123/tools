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
 * @TableName equipmenttree
 */
@TableName(value ="equipmenttree")
@Data
public class EquipmenttreePo implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 风机ID
     */
    private String fanid;

    /**
     * 设备编号
     */
    @JSONField(name = "equipmentCode")
    private String equipmentcode;

    /**
     * 设备名称
     */
    private String equipmentname;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 遥信状态
     */
    @JSONField(name = "telecommunicationState")
    private String telecommunicationstate;

    /**
     * 遥控状态
     */
    @JSONField(name = "telecontrolState")
    private String telecontrolstate;

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

    /**
     * 模型构建
     */
    private String glid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.example.demo.dao;

import com.example.demo.po.SisUpgradeVoltageTransformerData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.SisUpgradeVoltageTransformerData2;

/**
* @author 33099
* @description 针对表【SIS_upgrade_voltage_transformer_data】的数据库操作Service
* @createDate 2025-02-12 14:05:42
*/
public interface SisUpgradeVoltageTransformerDataDao extends IService<SisUpgradeVoltageTransformerData> {
    SisUpgradeVoltageTransformerData getLastData();
}

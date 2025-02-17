package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.DcsBoilerMonitoring;
import com.example.demo.po.DcsMonitoringOfAuxiliaryTurbines;
import com.example.demo.dao.DcsMonitoringOfAuxiliaryTurbinesDao;
import com.example.demo.mapper.DcsMonitoringOfAuxiliaryTurbinesMapper;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【DCS_monitoring_of_auxiliary_turbines】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class DcsMonitoringOfAuxiliaryTurbinesDaoImpl extends ServiceImpl<DcsMonitoringOfAuxiliaryTurbinesMapper, DcsMonitoringOfAuxiliaryTurbines>
    implements DcsMonitoringOfAuxiliaryTurbinesDao {
    @Override
    public DcsMonitoringOfAuxiliaryTurbines getLastData() {
        DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines = baseMapper.selectOne(Wrappers.<DcsMonitoringOfAuxiliaryTurbines>lambdaQuery()
                .orderByDesc(DcsMonitoringOfAuxiliaryTurbines::getInsertTime).last("limit 1"));
        return dcsMonitoringOfAuxiliaryTurbines;
    }
}





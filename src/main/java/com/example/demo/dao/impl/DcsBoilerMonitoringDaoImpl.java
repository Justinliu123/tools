package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.DcsBoilerMonitoring;
import com.example.demo.dao.DcsBoilerMonitoringDao;
import com.example.demo.mapper.DcsBoilerMonitoringMapper;
import com.example.demo.po.DcsBoilerMonitoring2;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【DCS_boiler_monitoring】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class DcsBoilerMonitoringDaoImpl extends ServiceImpl<DcsBoilerMonitoringMapper, DcsBoilerMonitoring>
    implements DcsBoilerMonitoringDao {

    @Override
    public DcsBoilerMonitoring getLastData() {
        DcsBoilerMonitoring dcsBoilerMonitoring = baseMapper.selectOne(Wrappers.<DcsBoilerMonitoring>lambdaQuery()
                .orderByDesc(DcsBoilerMonitoring::getInsertTime).last("limit 1"));
        return dcsBoilerMonitoring;
    }
}





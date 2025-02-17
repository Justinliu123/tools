package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.DcsTurbineData2Dao;
import com.example.demo.dao.DcsTurbineDataDao;
import com.example.demo.mapper.DcsTurbineData2Mapper;
import com.example.demo.mapper.DcsTurbineDataMapper;
import com.example.demo.po.DcsTurbineData;
import com.example.demo.po.DcsTurbineData2;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【DCS_turbine_data】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class DcsTurbineData2DaoImpl extends ServiceImpl<DcsTurbineData2Mapper, DcsTurbineData2>
    implements DcsTurbineData2Dao {
    @Override
    public DcsTurbineData2 getLastData() {
        DcsTurbineData2 dcsTurbineData = baseMapper.selectOne(Wrappers.<DcsTurbineData2>lambdaQuery()
                .orderByDesc(DcsTurbineData2::getInsertTime).last("limit 1"));
        return dcsTurbineData;
    }
}





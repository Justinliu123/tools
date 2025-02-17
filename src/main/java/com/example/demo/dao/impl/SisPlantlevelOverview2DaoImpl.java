package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.SisPlantlevelOverview2Dao;
import com.example.demo.mapper.SisPlantlevelOverview2Mapper;
import com.example.demo.po.SisPlantlevelOverview;
import com.example.demo.po.SisPlantlevelOverview2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author 33099
* @description 针对表【SIS_plantlevel_overview】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class SisPlantlevelOverview2DaoImpl extends ServiceImpl<SisPlantlevelOverview2Mapper, SisPlantlevelOverview2>
    implements SisPlantlevelOverview2Dao {

    @Override
    public List<SisPlantlevelOverview2> getHistoryData(Integer historyNum) {
        List<SisPlantlevelOverview2> result = new ArrayList<>();
        if (historyNum != null && historyNum > 0) {
            result = lambdaQuery().orderByDesc(SisPlantlevelOverview2::getId).last("limit " + historyNum).list();
        }
        return result;
    }

    @Override
    public SisPlantlevelOverview2 getLastData() {
        SisPlantlevelOverview2 sisPlantlevelOverview = baseMapper.selectOne(Wrappers.<SisPlantlevelOverview2>lambdaQuery()
                .orderByDesc(SisPlantlevelOverview2::getInsertTime).last("limit 1"));
        return sisPlantlevelOverview;
    }
}





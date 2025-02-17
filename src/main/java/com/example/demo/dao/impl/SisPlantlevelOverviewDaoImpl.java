package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.SisPlantlevelOverview;
import com.example.demo.dao.SisPlantlevelOverviewDao;
import com.example.demo.mapper.SisPlantlevelOverviewMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 33099
* @description 针对表【SIS_plantlevel_overview】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class SisPlantlevelOverviewDaoImpl extends ServiceImpl<SisPlantlevelOverviewMapper, SisPlantlevelOverview>
    implements SisPlantlevelOverviewDao {

    @Override
    public List<SisPlantlevelOverview> getHistoryData(Integer historyNum) {
        List<SisPlantlevelOverview> result = new ArrayList<>();
        if (historyNum != null && historyNum > 0) {
            result = lambdaQuery().orderByDesc(SisPlantlevelOverview::getId).last("limit " + historyNum).list();
        }
        return result;
    }

    @Override
    public SisPlantlevelOverview getLastData() {
        SisPlantlevelOverview sisPlantlevelOverview = baseMapper.selectOne(Wrappers.<SisPlantlevelOverview>lambdaQuery()
                .orderByDesc(SisPlantlevelOverview::getInsertTime).last("limit 1"));
        return sisPlantlevelOverview;
    }
}





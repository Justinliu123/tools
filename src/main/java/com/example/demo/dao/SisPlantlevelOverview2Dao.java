package com.example.demo.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.SisPlantlevelOverview2;

import java.util.List;

/**
* @author 33099
* @description 针对表【SIS_plantlevel_overview】的数据库操作Service
* @createDate 2025-02-12 14:05:42
*/
public interface SisPlantlevelOverview2Dao extends IService<SisPlantlevelOverview2> {

    List<SisPlantlevelOverview2> getHistoryData(Integer historyNum);

    SisPlantlevelOverview2 getLastData();
}

package com.example.demo.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.SisPlantlevelOverview2;
import com.example.demo.po.SisTurbineSystem2;

/**
* @author 33099
* @description 针对表【SIS_turbine_system】的数据库操作Service
* @createDate 2025-02-12 14:05:42
*/
public interface SisTurbineSystem2Dao extends IService<SisTurbineSystem2> {
    SisTurbineSystem2 getLastData();
}

package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.SisTurbineSystem;
import com.example.demo.dao.SisTurbineSystemDao;
import com.example.demo.mapper.SisTurbineSystemMapper;
import com.example.demo.po.SisTurbineSystem2;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【SIS_turbine_system】的数据库操作Service实现
* @createDate 2025-02-12 14:05:42
*/
@Service
public class SisTurbineSystemDaoImpl extends ServiceImpl<SisTurbineSystemMapper, SisTurbineSystem>
    implements SisTurbineSystemDao {
    @Override
    public SisTurbineSystem getLastData() {
        SisTurbineSystem sisTurbineSystem = baseMapper.selectOne(Wrappers.<SisTurbineSystem>lambdaQuery()
                .orderByDesc(SisTurbineSystem::getInsertTime).last("limit 1"));
        return sisTurbineSystem;
    }
}





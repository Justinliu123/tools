package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.ElectricmachinedPo;
import com.example.demo.dao.ElectricmachinedDao;
import com.example.demo.mapper.ElectricmachinedMapper;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【electricmachined(整体工况，用在“电场工况仿真”整体工况状态界面（参考）   的 第二部分)】的数据库操作Service实现
* @createDate 2024-12-17 11:30:11
*/
@Service
public class ElectricmachinedDaoImpl extends ServiceImpl<ElectricmachinedMapper, ElectricmachinedPo>
    implements ElectricmachinedDao {

}





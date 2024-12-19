package com.example.demo.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.po.ScencePo;
import com.example.demo.dao.ScenceDao;
import com.example.demo.mapper.ScenceMapper;
import org.springframework.stereotype.Service;

/**
* @author 33099
* @description 针对表【scence(整体工况，用在“电场工况仿真”整体工况状态界面（参考）   的 第二部分)】的数据库操作Service实现
* @createDate 2024-12-16 17:34:31
*/
@Service
public class ScenceDaoImpl extends ServiceImpl<ScenceMapper, ScencePo>
    implements ScenceDao {

}





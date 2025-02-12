// 创建一个类继承MetaObjectHandle接口
// 并实现里面的insertFill和updateFill方法
package com.example.demo.config;
 
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
 
import java.time.LocalDateTime;
 
@Component
@Slf4j
public class MyMataObjectHandle implements MetaObjectHandler {
 
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("insertTime", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
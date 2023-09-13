package com.example.demo.tool.pool;

/**
 * 对象生成工厂接口
 * 实现create方法通过D类型入参构建KeyObject对象
 * @author 33099
 * @date 2023/09/13
 */
public interface KeyObjectFactory<D, T extends KeyObject>{
    /**
     * 通过参数构建KeyObject对象
     * @param paramObject
     * @return {@link T}
     */
    T create(D paramObject);
}

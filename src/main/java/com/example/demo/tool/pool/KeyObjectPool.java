package com.example.demo.tool.pool;

import cn.hutool.cache.CacheListener;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 一种Key对应对象的连接池，
 * 无最大连接限制，当一个对象空闲超过指定时间后清除对象，
 * 空闲定义为一段时间内没有借用该对象
 *
 * 依赖hutool-cache 非线程安全
 * @author 33099
 * @date 2023/09/13
 */
@Data
public class KeyObjectPool<D extends ParamObject, T extends KeyObject> {
    /**
     * 默认允许空闲时间
     */
    private static final Long DEFAULT_IDLE_TIME = 1000 * 60 * 30L;

    private TimedCache<String, T> overdueTimer;
    private KeyObjectFactory<D, T> keyObjectFactory;

    /**
     * 标记KeyObject是否被借用
     */
    private Map<String, Boolean> flagObject = new ConcurrentHashMap<>();

    public KeyObjectPool(KeyObjectFactory<D, T> keyObjectFactory) {
        this(DEFAULT_IDLE_TIME, keyObjectFactory);
    }

    public KeyObjectPool(long idleTime, KeyObjectFactory<D, T> keyObjectFactory) {
        this.keyObjectFactory = keyObjectFactory;
        overdueTimer = CacheUtil.newTimedCache(idleTime);
        overdueTimer.setListener(new KeyObjectRemoveListener());
    }

    /**
     * 获取一个keyObject连接对象
     *
     * @return keyObject连接对象
     */
    @Nullable
    public synchronized T borrowObject(D paramObject) {
        if(flagObject.get(paramObject.getKey()) != null && flagObject.get(paramObject.getKey())) {
            return null;
        }
        T keyObject = overdueTimer.get(paramObject.getKey());
        if(keyObject == null) {
            keyObject = keyObjectFactory.create(paramObject);
        }
        overdueTimer.put(paramObject.getKey(), keyObject);
        flagObject.put(paramObject.getKey(), true);
        return keyObject;
    }

    /**
     * 归还一个keyObject连接对象
     *
     * @param keyObject
     */
    public void returnObject(T keyObject) {
        if (keyObject != null) {
            flagObject.put(keyObject.getKey(), false);
        }
    }

    /**
     * 监听空闲超时没有使用
     * @author 33099
     * @date 2023/09/13
     */
    public class KeyObjectRemoveListener implements CacheListener<String, T> {
        @Override
        public void onRemove(String key, T cachedObject) {
            cachedObject.close();
        }
    }
}

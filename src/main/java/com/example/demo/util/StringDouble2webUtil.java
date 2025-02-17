package com.example.demo.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * String类型的Double值转为各种格式
 *
 * @author liupanlong
 * @date 2025/02/14
 */
public class StringDouble2webUtil {
    /**
     * 获取百分比
     *
     * @param doubleStr 双str
     * @return {@link String }
     */
    public static String getPercentage(String doubleStr) {
        if(!StringUtils.hasLength(doubleStr)) return "";
        // 计算锅炉效率 取小数点后两位
        BigDecimal efficiency = new BigDecimal(doubleStr);
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal result = hundred.multiply(efficiency);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

}

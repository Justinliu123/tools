package com.example.demo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DoubleLineChartVo<X, Y> {
    @Data
    public static class YData<Y>{
        private List<Y> 供电煤耗;
        private List<Y> 发电煤耗;
    }
    private List<X> xData;
    private YData<Y> yData;
}

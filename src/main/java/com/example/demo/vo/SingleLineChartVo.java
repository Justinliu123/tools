package com.example.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class SingleLineChartVo<X, Y> {
    private List<X> xData;
    private List<Y> yData;
}

package com.example.demo.controller.used;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.common.exception.ResponseMessage;
import com.example.demo.service.SubSystemService;
import com.example.demo.vo.DoubleLineChartVo;
import com.example.demo.vo.SingleLineChartVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/sub-system")
public class SubSystemController {
    @Autowired
    private SubSystemService subSystemService;

    /**
     * 获取机组1的实时负载曲线的历史数据
     *
     * @param historyNum 历史数据条数
     * @return {@link String }
     */
    @GetMapping("/load-chart1")
    @ApiOperation(value = "厂级总览-实时负载曲线-厂1")
    public ResponseMessage<SingleLineChartVo> getLoadChart1(@RequestParam("historyNum") Integer historyNum) {
        SingleLineChartVo singleLineChartVo = subSystemService.getLoadChart1(historyNum);
        return ResponseMessage.success(singleLineChartVo);
    }

    /**
     * 获取机组2的实时负载曲线的历史数据
     *
     * @param historyNum 历史数据条数
     * @return {@link String }
     */
    @GetMapping("/load-chart2")
    @ApiOperation(value = "厂级总览-实时负载曲线-厂2")
    public ResponseMessage<SingleLineChartVo> getLoadChart2(@RequestParam("historyNum") Integer historyNum) {
        SingleLineChartVo singleLineChartVo = subSystemService.getLoadChart2(historyNum);
        return ResponseMessage.success(singleLineChartVo);
    }

    /**
     * 获取机组1的供电煤耗、发电煤耗的历史数据
     *
     * @param historyNum 历史数据条数
     * @return {@link String }
     */
    @GetMapping("/coal-consumption1")
    @ApiOperation(value = "厂级总览-供电煤耗/发电煤耗-厂1")
    public ResponseMessage<DoubleLineChartVo> coalConsumption1(@RequestParam("historyNum") Integer historyNum) {
        DoubleLineChartVo doubleLineChartVo = subSystemService.coalConsumption1(historyNum);
        return ResponseMessage.success(doubleLineChartVo);
    }

    /**
     * 获取机组2的供电煤耗、发电煤耗的历史数据
     *
     * @param historyNum 历史数据条数
     * @return {@link String }
     */
    @GetMapping("/coal-consumption2")
    @ApiOperation(value = "厂级总览-供电煤耗/发电煤耗-厂2")
    public ResponseMessage<DoubleLineChartVo> coalConsumption2(@RequestParam("historyNum") Integer historyNum) {
        DoubleLineChartVo doubleLineChartVo = subSystemService.coalConsumption2(historyNum);
        return ResponseMessage.success(doubleLineChartVo);
    }

    /**
     * 获取机组1的锅炉效率
     *
     * @return {@link String }
     */
    @GetMapping("/boiler-efficiency1")
    @ApiOperation(value = "厂级总览-锅炉效率-厂1")
    public ResponseMessage<String> boilerEfficiency1() {
        String result = subSystemService.boilerEfficiency1();
        return ResponseMessage.success(result, "success");
    }

    /**
     * 获取机组2的锅炉效率
     *
     * @return {@link String }
     */
    @GetMapping("/boiler-efficiency2")
    @ApiOperation(value = "厂级总览-锅炉效率-厂2")
    public ResponseMessage<String> boilerEfficiency2() {
        String result = subSystemService.boilerEfficiency2();
        return ResponseMessage.success(result, "success");
    }

    /**
     * 获取厂级总览的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("/factory-level-overview")
    @ApiOperation(value = "厂级总览-表格")
    public ResponseMessage<JSONArray> factoryLevelOverview() {
        JSONArray result = subSystemService.factoryLevelOverview();
        return ResponseMessage.success(result);
    }

    /**
     * 获取SIS系统数据分析的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("/sis-system-data-analysis")
    @ApiOperation(value = "SIS系统数据分析-表格")
    public ResponseMessage<JSONArray> SisSystemDataAnalysis() {
        JSONArray result = subSystemService.SisSystemDataAnalysis();
        return ResponseMessage.success(result);
    }

    /**
     * 获取升压站数据表的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("/booster-station-data")
    @ApiOperation(value = "升压站数据-表格")
    public ResponseMessage<JSONArray> boosterStationData() {
        JSONArray result = subSystemService.boosterStationData();
        return ResponseMessage.success(result);
    }

    /**
     * 获取锅炉监控数据表的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("/boiler-monitoring-data")
    @ApiOperation(value = "锅炉监控数据-表格")
    public ResponseMessage<JSONArray> boilerMonitoringData() {
        JSONArray result = subSystemService.boilerMonitoringData();
        return ResponseMessage.success(result);
    }

    /**
     * 获取汽轮机数据表的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("turbine-data")
    @ApiOperation(value = "汽轮机数据-表格")
    public ResponseMessage<JSONArray> turbineData() {
        JSONArray result = subSystemService.turbineData();
        return ResponseMessage.success(result);
    }

    /**
     * 获取辅机监控系统的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("machinery-monitoring-system")
    @ApiOperation(value = "辅机监控系统-表格")
    public ResponseMessage<JSONArray> auxiliaryMachineryMonitoringSystem() {
        JSONArray result = subSystemService.auxiliaryMachineryMonitoringSystem();
        return ResponseMessage.success(result);
    }

    /**
     * 获取辅机监控系统2的最新历史数据
     *
     * @return {@link String }
     */
    @GetMapping("machinery-monitoring-system2")
    @ApiOperation(value = "辅机监控系统2-表格")
    public ResponseMessage<JSONArray> auxiliaryMachineryMonitoringSystem2() {
        JSONArray result = subSystemService.auxiliaryMachineryMonitoringSystem2();
        return ResponseMessage.success(result);
    }
}

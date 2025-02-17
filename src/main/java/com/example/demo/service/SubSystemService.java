package com.example.demo.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.dao.*;
import com.example.demo.po.*;
import com.example.demo.task.WebSocketSendTask;
import com.example.demo.util.StringDouble2webUtil;
import com.example.demo.vo.DoubleLineChartVo;
import com.example.demo.vo.SingleLineChartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubSystemService {

    @Autowired
    private SisPlantlevelOverviewDao sisPlantlevelOverviewDao;

    @Autowired
    private SisPlantlevelOverview2Dao sisPlantlevelOverview2Dao;

    @Autowired
    private SisTurbineSystemDao sisTurbineSystemDao;

    @Autowired
    private SisTurbineSystem2Dao sisTurbineSystem2Dao;

    @Autowired
    private SisUpgradeVoltageTransformerDataDao sisUpgradeVoltageTransformerDataDao;

    @Autowired
    private SisUpgradeVoltageTransformerData2Dao sisUpgradeVoltageTransformerData2Dao;

    @Autowired
    private DcsTurbineDataDao dcsTurbineDataDao;

    @Autowired
    private DcsTurbineData2Dao dcsTurbineData2Dao;

    @Autowired
    private DcsMonitoringOfAuxiliaryTurbines2Dao dcsMonitoringOfAuxiliaryTurbines2Dao;

    @Autowired
    private DcsMonitoringOfAuxiliaryTurbinesDao dcsMonitoringOfAuxiliaryTurbinesDao;

    @Autowired
    private DcsBoilerMonitoring2Dao dcsBoilerMonitoring2Dao;

    @Autowired
    private DcsBoilerMonitoringDao dcsBoilerMonitoringDao;

    /**
     * 获取机组1的实时负载曲线的历史数据
     * 获取最近的historyNum条数据
     *
     * @param historyNum 历史数据条数
     * @return {@link String }
     */
    public SingleLineChartVo<LocalDateTime, Double> getLoadChart1(Integer historyNum) {
        SingleLineChartVo<LocalDateTime, Double> singleLineChartVo = new SingleLineChartVo();

        List<SisPlantlevelOverview> historyData = sisPlantlevelOverviewDao.getHistoryData(historyNum);
        if (historyData != null) {
            // 按时间升序排序
            historyData.sort(Comparator.comparing(SisPlantlevelOverview::getInsertTime));

            List<LocalDateTime> xData = historyData.stream().map(SisPlantlevelOverview::getInsertTime).collect(Collectors.toList());
            singleLineChartVo.setXData(xData);
            List<Double> yData = historyData.stream().map(SisPlantlevelOverview::getLoads).map(Double::valueOf).collect(Collectors.toList());
            singleLineChartVo.setYData(yData);
        }
        return singleLineChartVo;
    }

    public SingleLineChartVo<LocalDateTime, Double> getLoadChart2(Integer historyNum) {
        SingleLineChartVo<LocalDateTime, Double> singleLineChartVo = new SingleLineChartVo();

        List<SisPlantlevelOverview2> historyData = sisPlantlevelOverview2Dao.getHistoryData(historyNum);
        if (historyData != null) {
            // 按时间升序排序
            historyData.sort(Comparator.comparing(SisPlantlevelOverview2::getInsertTime));

            List<LocalDateTime> xData = historyData.stream().map(SisPlantlevelOverview2::getInsertTime).collect(Collectors.toList());
            singleLineChartVo.setXData(xData);
            List<Double> yData = historyData.stream().map(SisPlantlevelOverview2::getLoads).map(Double::valueOf).collect(Collectors.toList());
            singleLineChartVo.setYData(yData);
        }
        return singleLineChartVo;
    }

    public DoubleLineChartVo<LocalDateTime, Double> coalConsumption1(Integer historyNum) {
        DoubleLineChartVo<LocalDateTime, Double> doubleLineChartVo = new DoubleLineChartVo<>();
        List<SisPlantlevelOverview> historyData = sisPlantlevelOverviewDao.getHistoryData(historyNum);
        if (historyData != null) {
            historyData.sort(Comparator.comparing(SisPlantlevelOverview::getInsertTime));
            DoubleLineChartVo.YData<Double> yData = new DoubleLineChartVo.YData<>();
            // Line1为供电煤耗
            yData.set供电煤耗(historyData.stream().map(SisPlantlevelOverview::getPowerSupplyCoalConsumption).map(Double::valueOf).collect(Collectors.toList()));
            // Line2为发电煤耗
            yData.set发电煤耗(historyData.stream().map(SisPlantlevelOverview::getCoalConsumptionForPowerGeneration).map(Double::valueOf).collect(Collectors.toList()));
            doubleLineChartVo.setYData(yData);
            doubleLineChartVo.setXData(historyData.stream().map(SisPlantlevelOverview::getInsertTime).collect(Collectors.toList()));
        }
        return doubleLineChartVo;
    }

    public DoubleLineChartVo<LocalDateTime, Double> coalConsumption2(Integer historyNum) {
        DoubleLineChartVo<LocalDateTime, Double> doubleLineChartVo = new DoubleLineChartVo<>();
        List<SisPlantlevelOverview2> historyData = sisPlantlevelOverview2Dao.getHistoryData(historyNum);
        if (historyData != null) {
            historyData.sort(Comparator.comparing(SisPlantlevelOverview2::getInsertTime));
            DoubleLineChartVo.YData<Double> yData = new DoubleLineChartVo.YData<>();
            // Line1为供电煤耗
            yData.set供电煤耗(historyData.stream().map(SisPlantlevelOverview2::getPowerSupplyCoalConsumption).map(Double::valueOf).collect(Collectors.toList()));
            // Line2为发电煤耗
            yData.set发电煤耗(historyData.stream().map(SisPlantlevelOverview2::getCoalConsumptionForPowerGeneration).map(Double::valueOf).collect(Collectors.toList()));
            doubleLineChartVo.setYData(yData);
            doubleLineChartVo.setXData(historyData.stream().map(SisPlantlevelOverview2::getInsertTime).collect(Collectors.toList()));
        }
        return doubleLineChartVo;
    }

    public String boilerEfficiency1() {
        // 获取最新的一条数据
        SisPlantlevelOverview sisPlantlevelOverview = sisPlantlevelOverviewDao.getLastData();
        if (sisPlantlevelOverview != null) {
            // 计算锅炉效率 取小数点后两位
            return StringDouble2webUtil.getPercentage(sisPlantlevelOverview.getBoilerEfficiency());
        } else {
            return "0";
        }
    }

    public String boilerEfficiency2() {
        // 获取最新的一条数据
        SisPlantlevelOverview2 sisPlantlevelOverview2 = sisPlantlevelOverview2Dao.getLastData();
        if (sisPlantlevelOverview2 != null) {
            return StringDouble2webUtil.getPercentage(sisPlantlevelOverview2.getBoilerEfficiency());
        } else {
            return "0";
        }
    }

    public JSONArray factoryLevelOverview() {
        // 获取最新的一条数据
        SisPlantlevelOverview2 sisPlantlevelOverview2 = sisPlantlevelOverview2Dao.getLastData();
        SisPlantlevelOverview sisPlantlevelOverview = sisPlantlevelOverviewDao.getLastData();
        return WebSocketSendTask.makeChart1Data(sisPlantlevelOverview, sisPlantlevelOverview2);
    }

    public JSONArray SisSystemDataAnalysis() {
        // 获取最新的一条数据
        SisTurbineSystem sisTurbineSystem = sisTurbineSystemDao.getLastData();
        SisTurbineSystem2 sisTurbineSystem2 = sisTurbineSystem2Dao.getLastData();
        return WebSocketSendTask.makeChart2Data(sisTurbineSystem, sisTurbineSystem2);
    }

    public JSONArray boosterStationData() {
        SisUpgradeVoltageTransformerData sisUpgradeVoltageTransformerData = sisUpgradeVoltageTransformerDataDao.getLastData();
        SisUpgradeVoltageTransformerData2 sisUpgradeVoltageTransformerData2 = sisUpgradeVoltageTransformerData2Dao.getLastData();
        return WebSocketSendTask.makeChart3Data(sisUpgradeVoltageTransformerData, sisUpgradeVoltageTransformerData2);
    }

    public JSONArray boilerMonitoringData() {
        DcsBoilerMonitoring dcsBoilerMonitoring = dcsBoilerMonitoringDao.getLastData();
        DcsBoilerMonitoring2 dcsBoilerMonitoring2 = dcsBoilerMonitoring2Dao.getLastData();
        return WebSocketSendTask.makeChart4Data(dcsBoilerMonitoring, dcsBoilerMonitoring2);
    }

    public JSONArray turbineData() {
        DcsTurbineData dcsTurbineData = dcsTurbineDataDao.getLastData();
        DcsTurbineData2 dcsTurbineData2 = dcsTurbineData2Dao.getLastData();
        return WebSocketSendTask.makeChart5Data(dcsTurbineData, dcsTurbineData2);
    }

    public JSONArray auxiliaryMachineryMonitoringSystem() {
        DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines = dcsMonitoringOfAuxiliaryTurbinesDao.getLastData();
        DcsMonitoringOfAuxiliaryTurbines2 dcsMonitoringOfAuxiliaryTurbines2 = dcsMonitoringOfAuxiliaryTurbines2Dao.getLastData();
        return WebSocketSendTask.makeChart6Data(dcsMonitoringOfAuxiliaryTurbines, dcsMonitoringOfAuxiliaryTurbines2);
    }

    public JSONArray auxiliaryMachineryMonitoringSystem2() {
        DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines = dcsMonitoringOfAuxiliaryTurbinesDao.getLastData();
        DcsMonitoringOfAuxiliaryTurbines2 dcsMonitoringOfAuxiliaryTurbines2 = dcsMonitoringOfAuxiliaryTurbines2Dao.getLastData();
        return WebSocketSendTask.makeChart7Data(dcsMonitoringOfAuxiliaryTurbines, dcsMonitoringOfAuxiliaryTurbines2);
    }
}

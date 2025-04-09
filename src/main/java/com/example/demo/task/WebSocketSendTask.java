package com.example.demo.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.constant.ChartNumConstants;
import com.example.demo.constant.RedisTopicConstants;
import com.example.demo.dto.UserDto;
import com.example.demo.po.*;
import com.example.demo.util.RedisTemplateUtil;
import com.example.demo.util.StringDouble2webUtil;
import com.example.demo.websocket.WebSocketUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


@Component
@Slf4j
public class WebSocketSendTask {
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Scheduled(cron = "0/1 * * * * ?")
    public void sendMessage(){
        // 检查是否需要推送表一数据
        if (WebSocketUsers.hasChartSession(ChartNumConstants.CHART1) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW);
            SisPlantlevelOverview sisPlantlevelOverview = (SisPlantlevelOverview) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW_2);
            SisPlantlevelOverview2 sisPlantlevelOverview2 = (SisPlantlevelOverview2) cacheObject2;

            // 发送厂级总览数据
            JSONArray jsonArray = makeChart1Data(sisPlantlevelOverview, sisPlantlevelOverview2);
            WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART1, JSONObject.toJSONString(jsonArray));
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_PLANTLEVEL_OVERVIEW_2);
        }
        // 检查是否需要推送表二数据
        if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART2) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM);
            SisTurbineSystem sisTurbineSystem = (SisTurbineSystem) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM_2);
            SisTurbineSystem2 sisTurbineSystem2 = (SisTurbineSystem2) cacheObject2;

            // 发送SIS系统数据分析数据
            JSONArray jsonArray2 = makeChart2Data(sisTurbineSystem, sisTurbineSystem2);
            WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART2, JSONObject.toJSONString(jsonArray2));
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_TURBINE_SYSTEM_2);
        }
        // 检查是否需要推送表三数据
        if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART3) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA);
            SisUpgradeVoltageTransformerData sisUpgradeVoltageTransformerData = (SisUpgradeVoltageTransformerData) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA_2);
            SisUpgradeVoltageTransformerData2 sisUpgradeVoltageTransformerData2 = (SisUpgradeVoltageTransformerData2) cacheObject2;

            // 发送升压站数据
            JSONArray jsonArray3 = makeChart3Data(sisUpgradeVoltageTransformerData, sisUpgradeVoltageTransformerData2);
            WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART3, JSONObject.toJSONString(jsonArray3));
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.SIS_UPGRADE_VOLTAGE_TRANSFORMER_DATA_2);
        }
        // 检查是否需要推送表四数据
        if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART4) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING);
            DcsBoilerMonitoring dcsBoilerMonitoring = (DcsBoilerMonitoring) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING_2);
            DcsBoilerMonitoring2 dcsBoilerMonitoring2 = (DcsBoilerMonitoring2) cacheObject2;

            // 发送锅炉监控数据
            JSONArray jsonArray4 = makeChart4Data(dcsBoilerMonitoring, dcsBoilerMonitoring2);
            WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART4, JSONObject.toJSONString(jsonArray4));
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_BOILER_MONITORING_2);
        }
        // 检查是否需要推送表五数据
        if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART5) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA);
            DcsTurbineData dcsTurbineData = (DcsTurbineData) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA_2);
            DcsTurbineData2 dcsTurbineData2 = (DcsTurbineData2) cacheObject2;

            // 发送汽轮机数据
            JSONArray jsonArray5 = makeChart5Data(dcsTurbineData, dcsTurbineData2);
            WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART5, JSONObject.toJSONString(jsonArray5));
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_TURBINE_DATA_2);
        }
        // 检查是否需要推送表六数据
        if((WebSocketUsers.hasChartSession(ChartNumConstants.CHART6) || WebSocketUsers.hasChartSession(ChartNumConstants.CHART7)) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES) &&
                redisTemplateUtil.hasKey(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES_2)) {
            Object cacheObject = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES);
            DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines = (DcsMonitoringOfAuxiliaryTurbines) cacheObject;
            Object cacheObject2 = redisTemplateUtil.getCacheObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES_2);
            DcsMonitoringOfAuxiliaryTurbines2 dcsMonitoringOfAuxiliaryTurbines2 = (DcsMonitoringOfAuxiliaryTurbines2) cacheObject2;
            if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART6)) {
                JSONArray jsonArray6 = makeChart6Data(dcsMonitoringOfAuxiliaryTurbines, dcsMonitoringOfAuxiliaryTurbines2);
                WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART6, JSONObject.toJSONString(jsonArray6));
            }
            if(WebSocketUsers.hasChartSession(ChartNumConstants.CHART7)) {
                JSONArray jsonArray7 = makeChart7Data(dcsMonitoringOfAuxiliaryTurbines, dcsMonitoringOfAuxiliaryTurbines2);
                WebSocketUsers.sendMessageToUsersByText(ChartNumConstants.CHART7, JSONObject.toJSONString(jsonArray7));
            }
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES);
            redisTemplateUtil.deleteObject(RedisTopicConstants.redisTopic + RedisTopicConstants.DCS_MONITORING_OF_AUXILIARY_TURBINES_2);
        }
    }

    public static JSONArray makeChart7Data(DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines, DcsMonitoringOfAuxiliaryTurbines2 dcsMonitoringOfAuxiliaryTurbines2) {
        // 设置每个字段空值
        dcsMonitoringOfAuxiliaryTurbines.setEmptyFieldsToZero();
        dcsMonitoringOfAuxiliaryTurbines2.setEmptyFieldsToZero();
        JSONArray jsonArray = new JSONArray();
        // 机组SCR反应器2A脱烃后烟气N0x含量
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getNoxConcentrationInFlueGas());
        BigDecimal bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getNoxConcentrationInFlueGas());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "机组SCR反应器2A脱烃后烟气N0x含量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "mg/m^3");
        jsonArray.add(jsonObject);
        // 机组SCR反应器2A脱烃后烟气02含量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getO2ConcentrationInFlueGas());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getO2ConcentrationInFlueGas());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "机组SCR反应器2A脱烃后烟气02含量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "mg/m^3");
        // 机组SCR反应器2A脱烃后烟气NH3含量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getNh3ConcentrationInFlueGas());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getNh3ConcentrationInFlueGas());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "机组SCR反应器2A脱烃后烟气NH3含量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "mg/m^3");
        jsonArray.add(jsonObject);
        // SCR反应器的压力保持状况
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getPressureHoldingStatusOfTheScrReactor());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getPressureHoldingStatusOfTheScrReactor());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "SCR反应器的压力保持状况")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    public static JSONArray makeChart6Data(DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines, DcsMonitoringOfAuxiliaryTurbines2 dcsMonitoringOfAuxiliaryTurbines2) {
        dcsMonitoringOfAuxiliaryTurbines.setEmptyFieldsToZero();
        dcsMonitoringOfAuxiliaryTurbines2.setEmptyFieldsToZero();

        // 两个表格，需要嵌套数组
        JSONArray outArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();

        // 构造表1
        // 泵效率
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = null;
        BigDecimal bigDecimalValue_2 = null;
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "泵效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(dcsMonitoringOfAuxiliaryTurbines.getPumpEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(dcsMonitoringOfAuxiliaryTurbines2.getPumpEfficiency()))
                .fluentPut("unit", "%");
        jsonArray1.add(jsonObject);
        // 泵入口压力
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getPumpInletPressure());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getPumpInletPressure());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "泵入口压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "MPa");
        jsonArray1.add(jsonObject);
        // 泵出口压力
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getPumpOutletPressure());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getPumpOutletPressure());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "泵出口压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "MPa");
        jsonArray1.add(jsonObject);

        outArray.add(jsonArray1);
        // 构造表2
        // 送风机电动机电流
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getForcedDraftFanMotorCurrent());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getForcedDraftFanMotorCurrent());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "送风机电动机电流")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "A");
        jsonArray2.add(jsonObject);
        // 送风机电动机功率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getForcedDraftFanMotorPower());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getForcedDraftFanMotorPower());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "送风机电动机功率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kW");
        jsonArray2.add(jsonObject);
        // 送风机风压压力
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines.getForcedDraftFanAirPressure());
        bigDecimalValue_2 = new BigDecimal(dcsMonitoringOfAuxiliaryTurbines2.getForcedDraftFanAirPressure());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "送风机风压压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kPa");
        jsonArray2.add(jsonObject);
        outArray.add(jsonArray2);
        return outArray;
    }

    public static JSONArray makeChart5Data(DcsTurbineData dcsTurbineData, DcsTurbineData2 dcsTurbineData2) {
        dcsTurbineData.setEmptyFieldsToZero();
        dcsTurbineData2.setEmptyFieldsToZero();

        JSONArray jsonArray = new JSONArray();
        // 汽轮机效率
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = null;
        BigDecimal bigDecimalValue_2 = null;
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "汽轮机效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(dcsTurbineData.getSteamTurbineEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(dcsTurbineData2.getSteamTurbineEfficiency()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);
        // 发电机d轴电抗
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorDAxisReactance());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorDAxisReactance());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "发电机d轴电抗")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "p.u.");
        jsonArray.add(jsonObject);
        // 发电机q轴电抗
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorQAxisReactance());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorQAxisReactance());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "发电机q轴电抗")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "p.u.");
        jsonArray.add(jsonObject);
        // 发电机电阻
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorResistance());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorResistance());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "发电机电阻")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "Ω");
        jsonArray.add(jsonObject);
        // 发电机阻尼系数
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorDampingCoefficient());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorDampingCoefficient());
        jsonObject.fluentPut("num", "5")
                .fluentPut("name", "发电机阻尼系数")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);
        // 发电机转动惯量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorMomentOfInertia());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorMomentOfInertia());
        jsonObject.fluentPut("num", "6")
                .fluentPut("name", "发电机转动惯量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kg·m2");
        jsonArray.add(jsonObject);
        // 发电机d轴参考电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorDAxisReferenceVoltage());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorDAxisReferenceVoltage());
        jsonObject.fluentPut("num", "7")
                .fluentPut("name", "发电机d轴参考电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "V");
        jsonArray.add(jsonObject);
        // 发电机q轴参考电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getGeneratorQAxisReferenceVoltage());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getGeneratorQAxisReferenceVoltage());
        jsonObject.fluentPut("num", "8")
                .fluentPut("name", "发电机q轴参考电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "V");
        jsonArray.add(jsonObject);
        // 汽轮机应力值
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getSteamTurbineStressValue());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getSteamTurbineStressValue());
        jsonObject.fluentPut("num", "9")
                .fluentPut("name", "汽轮机应力值")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "MPa");
        jsonArray.add(jsonObject);
        // 汽轮机进气温度
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsTurbineData.getSteamTurbineInletSteamTemperature());
        bigDecimalValue_2 = new BigDecimal(dcsTurbineData2.getSteamTurbineInletSteamTemperature());
        jsonObject.fluentPut("num", "10")
                .fluentPut("name", "汽轮机进气温度")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "℃");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    public static JSONArray makeChart4Data(DcsBoilerMonitoring dcsBoilerMonitoring, DcsBoilerMonitoring2 dcsBoilerMonitoring2) {
        dcsBoilerMonitoring.setEmptyFieldsToZero();
        dcsBoilerMonitoring2.setEmptyFieldsToZero();

        JSONArray jsonArray = new JSONArray();
        // 锅炉出口温度
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getBoilerOutletPressure());
        BigDecimal bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getBoilerOutletPressure());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "锅炉出口温度")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "℃");
        jsonArray.add(jsonObject);
        // 锅炉出口压力
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getBoilerOutletPressure());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getBoilerOutletPressure());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "锅炉出口压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "MPa");
        jsonArray.add(jsonObject);
        // 锅炉出口焓
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getEnthalpyAtBoilerOutlet());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getEnthalpyAtBoilerOutlet());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "锅炉出口焓")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kJ/kg");
        jsonArray.add(jsonObject);
        // 锅炉右侧省煤器出口烟气含氧量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getAverageOxygenContentBoilerCombustion());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getAverageOxygenContentBoilerCombustion());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "锅炉右侧省煤器出口烟气含氧量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "ppm");
        jsonArray.add(jsonObject);
        // 锅炉左侧省煤器出口烟气含氧量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getAverageOxygenContentBoilerCombustion());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getAverageOxygenContentBoilerCombustion());
        jsonObject.fluentPut("num", "5")
                .fluentPut("name", "锅炉左侧省煤器出口烟气含氧量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "ppm");
        jsonArray.add(jsonObject);
        // 锅炉储水罐水位
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getWaterLevelInBoilerStorageTank());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getWaterLevelInBoilerStorageTank());
        jsonObject.fluentPut("num", "6")
                .fluentPut("name", "锅炉储水罐水位")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "m");
        jsonArray.add(jsonObject);
        // 锅炉入口风量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getAirFlowRateAtBoilerInlet());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getAirFlowRateAtBoilerInlet());
        jsonObject.fluentPut("num", "7")
                .fluentPut("name", "锅炉入口风量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "m3/h");
        jsonArray.add(jsonObject);
        // 锅炉入口风压力
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getAirPressureAtBoilerInlet());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getAirPressureAtBoilerInlet());
        jsonObject.fluentPut("num", "8")
                .fluentPut("name", "锅炉入口风压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "MPa");
        jsonArray.add(jsonObject);
        // 锅炉燃烧平均氧含量
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(dcsBoilerMonitoring.getAverageOxygenContentBoilerCombustion());
        bigDecimalValue_2 = new BigDecimal(dcsBoilerMonitoring2.getAverageOxygenContentBoilerCombustion());
        jsonObject.fluentPut("num", "9")
                .fluentPut("name", "锅炉燃烧平均氧含量")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "ppm");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    public static JSONArray makeChart3Data(SisUpgradeVoltageTransformerData sisUpgradeVoltageTransformerData, SisUpgradeVoltageTransformerData2 sisUpgradeVoltageTransformerData2) {
        // 设置每个为空的字段为0
        sisUpgradeVoltageTransformerData.setEmptyFieldsToZero();
        sisUpgradeVoltageTransformerData2.setEmptyFieldsToZero();
        JSONArray jsonArray = new JSONArray();

        // 升压变压器入口电压
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getBoostTransformerInputVoltage());
        BigDecimal bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getBoostTransformerInputVoltage());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "升压变压器入口电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kV");
        jsonArray.add(jsonObject);

        // 升压变压器出口电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getBoostTransformerOutputVoltage());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getBoostTransformerOutputVoltage());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "升压变压器出口电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kV");
        jsonArray.add(jsonObject);
        // 升压变压器输出电流
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getBoostTransformerOutputCurrent());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getBoostTransformerOutputCurrent());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "升压变压器输出电流")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "A");
        jsonArray.add(jsonObject);
        // 升压变压器输出功率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getBoostTransformerOutputPower());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getBoostTransformerOutputPower());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "升压变压器输出功率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kW");
        jsonArray.add(jsonObject);
        // 输电线输输电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLineVoltage());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLineVoltage());
        jsonObject.fluentPut("num", "5")
                .fluentPut("name", "输电线输输电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kV");
        jsonArray.add(jsonObject);
        // 输电线电流
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLineCurrent());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLineCurrent());
        jsonObject.fluentPut("num", "6")
                .fluentPut("name", "输电线电流")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "A");
        jsonArray.add(jsonObject);
        // 输电线功率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLinePower());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLinePower());
        jsonObject.fluentPut("num", "7")
                .fluentPut("name", "输电线功率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kW");
        jsonArray.add(jsonObject);
        // 降压变压器入口电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getStepDownTransformerInputVoltage());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getStepDownTransformerInputVoltage());
        jsonObject.fluentPut("num", "8")
                .fluentPut("name", "降压变压器入口电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kV");
        jsonArray.add(jsonObject);
        // 降压变压器出口电压
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getStepDownTransformerOutputVoltage());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getStepDownTransformerOutputVoltage());
        jsonObject.fluentPut("num", "9")
                .fluentPut("name", "降压变压器出口电压")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kV");
        jsonArray.add(jsonObject);
        // 降压变压器输出电流
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getStepDownTransformerOutputCurrent());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getStepDownTransformerOutputCurrent());
        jsonObject.fluentPut("num", "10")
                .fluentPut("name", "降压变压器输出电流")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "A");
        jsonArray.add(jsonObject);
        // 降压变压器输出功率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getStepDownTransformerOutputPower());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getStepDownTransformerOutputPower());
        jsonObject.fluentPut("num", "11")
                .fluentPut("name", "降压变压器输出功率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kW");
        jsonArray.add(jsonObject);
        // 输电线输送效率
        jsonObject = new JSONObject();
        jsonObject.fluentPut("num", "12")
                .fluentPut("name", "输电线输送效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(sisUpgradeVoltageTransformerData.getTransmissionLineEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(sisUpgradeVoltageTransformerData2.getTransmissionLineEfficiency()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);
        // 输电线连线状态
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLineConnectionStatus());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLineConnectionStatus());
        jsonObject.fluentPut("num", "13")
                .fluentPut("name", "输电线连线状态")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);
        // 输电线运行温度
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLineOperatingTemperature());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLineOperatingTemperature());
        jsonObject.fluentPut("num", "14")
                .fluentPut("name", "输电线运行温度")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "℃");
        jsonArray.add(jsonObject);
        // 输电线连线健康状态
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getTransmissionLineConnectionHealthStatus());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getTransmissionLineConnectionHealthStatus());
        jsonObject.fluentPut("num", "15")
                .fluentPut("name", "输电线连线健康状态")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);
        // 网络输出功率分配状态
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisUpgradeVoltageTransformerData.getNetworkOutputPowerAllocation());
        bigDecimalValue_2 = new BigDecimal(sisUpgradeVoltageTransformerData2.getNetworkOutputPowerAllocation());
        jsonObject.fluentPut("num", "16")
                .fluentPut("name", "网络输出功率分配状态")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);
        // 网络连接点功率平衡状态
        jsonObject = new JSONObject();
        jsonObject.fluentPut("num", "17")
                .fluentPut("name", "网络连接点功率平衡状态")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(sisUpgradeVoltageTransformerData.getPowerAtNetworkConnectionPoints()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(sisUpgradeVoltageTransformerData2.getPowerAtNetworkConnectionPoints()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    public static JSONArray makeChart2Data(SisTurbineSystem sisTurbineSystem, SisTurbineSystem2 sisTurbineSystem2) {
        sisTurbineSystem.setEmptyFieldsToZero();
        sisTurbineSystem2.setEmptyFieldsToZero();
        JSONArray jsonArray = new JSONArray();
        // 压力控制器团充注压力
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = new BigDecimal(sisTurbineSystem.getPressureControllerGroupChargingPressure());
        BigDecimal bigDecimalValue_2 = new BigDecimal(sisTurbineSystem2.getPressureControllerGroupChargingPressure());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "压力控制器团充注压力")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);

        // 机组增负载速率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisTurbineSystem.getUnitLoadIncreaseRate());
        bigDecimalValue_2 = new BigDecimal(sisTurbineSystem2.getUnitLoadIncreaseRate());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "机组增负载速率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);

        // 机组减负载速率
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisTurbineSystem.getUnitLoadDecreaseRate());
        bigDecimalValue_2 = new BigDecimal(sisTurbineSystem2.getUnitLoadDecreaseRate());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "机组减负载速率")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);

        // 运行调节器状态值
        jsonObject = new JSONObject();
        bigDecimalValue = new BigDecimal(sisTurbineSystem.getOperatingRegulatorStatusValue());
        bigDecimalValue_2 = new BigDecimal(sisTurbineSystem2.getOperatingRegulatorStatusValue());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "运行调节器状态值")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "");
        jsonArray.add(jsonObject);

        return jsonArray;
    }

    public static JSONArray makeChart1Data(SisPlantlevelOverview sisPlantlevelOverview, SisPlantlevelOverview2 sisPlantlevelOverview2) {
        sisPlantlevelOverview.setEmptyFieldsToZero();
        sisPlantlevelOverview2.setEmptyFieldsToZero();
        JSONArray jsonArray = new JSONArray();
        // 构造负荷
        JSONObject jsonObject = new JSONObject();
        BigDecimal bigDecimalValue = new BigDecimal(sisPlantlevelOverview.getLoads());
        BigDecimal bigDecimalValue_2 = new BigDecimal(sisPlantlevelOverview2.getLoads());
        jsonObject.fluentPut("num", "1")
                .fluentPut("name", "负荷")
                .fluentPut("1set", bigDecimalValue.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "WM");
        jsonArray.add(jsonObject);

        // 构造供电煤耗
        jsonObject = new JSONObject();
        BigDecimal bigDecimalValue2 = new BigDecimal(sisPlantlevelOverview.getPowerSupplyCoalConsumption());
        BigDecimal bigDecimalValue2_2 = new BigDecimal(sisPlantlevelOverview2.getPowerSupplyCoalConsumption());
        jsonObject.fluentPut("num", "2")
                .fluentPut("name", "供电煤耗")
                .fluentPut("1set", bigDecimalValue2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue2_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "g/kWh");
        jsonArray.add(jsonObject);

        // 构造发电煤耗
        jsonObject = new JSONObject();
        BigDecimal bigDecimalValue3 = new BigDecimal(sisPlantlevelOverview.getCoalConsumptionForPowerGeneration());
        BigDecimal bigDecimalValue3_2 = new BigDecimal(sisPlantlevelOverview2.getCoalConsumptionForPowerGeneration());
        jsonObject.fluentPut("num", "3")
                .fluentPut("name", "发电煤耗")
                .fluentPut("1set", bigDecimalValue3.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue3_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "g/kWh");
        jsonArray.add(jsonObject);

        // 构造厂用电率
        jsonObject = new JSONObject();
        BigDecimal bigDecimalValue4 = new BigDecimal(sisPlantlevelOverview.getAuxiliaryPowerRatio());
        BigDecimal bigDecimalValue4_2 = new BigDecimal(sisPlantlevelOverview2.getAuxiliaryPowerRatio());
        jsonObject.fluentPut("num", "4")
                .fluentPut("name", "厂用电率")
                .fluentPut("1set", bigDecimalValue4.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue4_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);

        // 构造锅炉效率
        jsonObject = new JSONObject();
        jsonObject.fluentPut("num", "5")
                .fluentPut("name", "锅炉效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview.getBoilerEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview2.getBoilerEfficiency()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);

        // 构造气机热耗
        jsonObject = new JSONObject();
        BigDecimal bigDecimalValue6 = new BigDecimal(sisPlantlevelOverview.getSteamTurbineHeatRate());
        BigDecimal bigDecimalValue6_2 = new BigDecimal(sisPlantlevelOverview2.getSteamTurbineHeatRate());
        jsonObject.fluentPut("num", "6")
                .fluentPut("name", "气机热耗")
                .fluentPut("1set", bigDecimalValue6.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue6_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "kJ/kWh");
        jsonArray.add(jsonObject);

        // 汽耗率
        jsonObject = new JSONObject();
        BigDecimal bigDecimalValue7 = new BigDecimal(sisPlantlevelOverview.getSteamConsumptionRate());
        BigDecimal bigDecimalValue7_2 = new BigDecimal(sisPlantlevelOverview2.getSteamConsumptionRate());
        jsonObject.fluentPut("num", "7")
                .fluentPut("name", "汽耗率")
                .fluentPut("1set", bigDecimalValue7.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("2set", bigDecimalValue7_2.setScale(2, RoundingMode.HALF_UP).toString())
                .fluentPut("unit", "Kg/kWh");
        jsonArray.add(jsonObject);

        // 汽机效率
        jsonObject = new JSONObject();
        jsonObject.fluentPut("num", "8")
                .fluentPut("name", "汽机效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview.getSteamTurbineEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview2.getSteamTurbineEfficiency()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);

        // 机组效率
        jsonObject = new JSONObject();
        jsonObject.fluentPut("num", "9")
                .fluentPut("name", "机组效率")
                .fluentPut("1set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview.getGeneratingUnitEfficiency()))
                .fluentPut("2set", StringDouble2webUtil.getPercentage(sisPlantlevelOverview2.getGeneratingUnitEfficiency()))
                .fluentPut("unit", "%");
        jsonArray.add(jsonObject);
        return jsonArray;
    }
}

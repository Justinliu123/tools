package com.example.demo.mqtt;

public class MqttConstant {
    static String redisKey = "topic:";
    static String dataTopic = "Scence";
    static String predictionTopic = "Richtech.GDNZ/windTurbine/predictionData";
    static String statusTopic = "Richtech.GDNz/pointStatus/data";
    static String equipmentTopic = "Electricmachined";
    static String equipmentStatusTopic = "Equipmenttree";
    // 光伏相关
    static String GfDataTopic = "GF.Scence";
    static String GfPredictionTopic = "GF.predictionData";
    static String GfEquipmentTopic = "GF.Electricmachined";
    static String GfEquipmentStatusTopic = "GF.Equipmenttree";
    // 火电相关
    static String HdDataTopic = "HD.Scence";
    static String HdEquipmentTopic = "HD.Electricmachined";
    static String HdEquipmentStatusTopic = "HD.Equipmenttree";

    // 1机组数据
    static String topic1 = "SIS_plantlevel_overview";
    static String topic2 = "SIS_turbine_system";
    static String topic3 = "SIS_upgrade_voltage_transformer_data";
    static String topic4 = "DCS_boiler_monitoring";
    static String topic5 = "DCS_turbine_data";
    static String topic6 = "DCS_monitoring_of_auxiliary_turbines";

    // 2机组数据
    static String topic21 = "SIS_plantlevel_overview_2";
    static String topic22 = "SIS_turbine_system_2";
    static String topic23 = "SIS_upgrade_voltage_transformer_data_2";
    static String topic24 = "DCS_boiler_monitoring_2";
    static String topic25 = "DCS_turbine_data_2";
    static String topic26 = "DCS_monitoring_of_auxiliary_turbines_2";

    static String[] topics = {topic1, topic2, topic3, topic4, topic5, topic6};
    static String[] topics2 = {topic21, topic22, topic23, topic24, topic25, topic26};
    //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
    static int[] qos = {0, 0, 0, 0, 0, 0};
    static int[] qos2 = {0, 0, 0, 0, 0, 0};
}

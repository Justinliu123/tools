package com.example.demo.mqtt;

public class MqttConstant {
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
    //风电主题
    static String[] topics = {dataTopic, predictionTopic, statusTopic, equipmentTopic, equipmentStatusTopic};
    static String[] gfTopics = {GfDataTopic, GfPredictionTopic, GfEquipmentTopic, GfEquipmentStatusTopic};
    static String[] hdTopics = {HdDataTopic, HdEquipmentTopic, HdEquipmentStatusTopic};
    //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
    static int[] qos = {0, 0, 0, 0, 0};
    static int[] gfQos = {0, 0, 0, 0};
    static int[] hdQos = {0, 0, 0};
}

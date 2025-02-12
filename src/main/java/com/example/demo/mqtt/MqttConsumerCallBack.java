package com.example.demo.mqtt;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.example.demo.dao.*;
import com.example.demo.po.*;
import com.example.demo.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
public class MqttConsumerCallBack implements MqttCallback {

    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("连接断开，正在重连");
        log.error(throwable.toString());
        throwable.printStackTrace();
        MqttConsumerConfig mqttConsumerConfig = SpringBeanUtil.getBean(MqttConsumerConfig.class);
        mqttConsumerConfig.connect();
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("接收消息主题 : %s", topic));
        System.out.println(String.format("接收消息内容 : %s", new String(message.getPayload())));
        if (topic.equals(MqttConstant.topic1)) {
            SisPlantlevelOverviewDao sisPlantlevelOverviewDao = SpringBeanUtil.getBean(SisPlantlevelOverviewDao.class);
            SisPlantlevelOverview sisPlantlevelOverview = JSONObject.parseObject(new String(message.getPayload()), SisPlantlevelOverview.class,
                    JSONReader.Feature.SupportSmartMatch);
            sisPlantlevelOverviewDao.save(sisPlantlevelOverview);
            log.info("SIS_plantlevel_overview 数据入库成功");
        } else if (topic.equals(MqttConstant.topic2)) {
            SisTurbineSystemDao sisTurbineSystemDao = SpringBeanUtil.getBean(SisTurbineSystemDao.class);
            SisTurbineSystem sisTurbineSystem = JSONObject.parseObject(new String(message.getPayload()), SisTurbineSystem.class,
                    JSONReader.Feature.SupportSmartMatch);
            sisTurbineSystemDao.save(sisTurbineSystem);
            log.info("SIS_turbine_system 数据入库成功");
        } else if (topic.equals(MqttConstant.topic3)) {
            SisUpgradeVoltageTransformerDataDao sisUpgradeVoltageTransformerDataDao = SpringBeanUtil.getBean(SisUpgradeVoltageTransformerDataDao.class);
            SisUpgradeVoltageTransformerData sisUpgradeVoltageTransformerData = JSONObject.parseObject(new String(message.getPayload()),
                    SisUpgradeVoltageTransformerData.class,
                    JSONReader.Feature.SupportSmartMatch);
            sisUpgradeVoltageTransformerDataDao.save(sisUpgradeVoltageTransformerData);
            log.info("SIS_upgrade_voltage_transformer_data 数据入库成功");
        } else if (topic.equals(MqttConstant.topic4)) {
            DcsBoilerMonitoringDao dcsBoilerMonitoringDao = SpringBeanUtil.getBean(DcsBoilerMonitoringDao.class);
            DcsBoilerMonitoring dcsBoilerMonitoring = JSONObject.parseObject(new String(message.getPayload()),
                    DcsBoilerMonitoring.class, JSONReader.Feature.SupportSmartMatch);
            dcsBoilerMonitoringDao.save(dcsBoilerMonitoring);
            log.info("DCS_boiler_monitoring 数据入库成功");
        } else if (topic.equals(MqttConstant.topic5)) {
            DcsTurbineDataDao dcsTurbineDataDao = SpringBeanUtil.getBean(DcsTurbineDataDao.class);
            DcsTurbineData dcsTurbineData = JSONObject.parseObject(new String(message.getPayload()), DcsTurbineData.class, JSONReader.Feature.SupportSmartMatch);
            dcsTurbineDataDao.save(dcsTurbineData);
            log.info("DCS_turbine_data 数据入库成功");
        } else if (topic.equals(MqttConstant.topic6)) {
            DcsMonitoringOfAuxiliaryTurbinesDao dcsMonitoringOfAuxiliaryTurbinesDao = SpringBeanUtil.getBean(DcsMonitoringOfAuxiliaryTurbinesDao.class);
            DcsMonitoringOfAuxiliaryTurbines dcsMonitoringOfAuxiliaryTurbines = JSONObject.parseObject(new String(message.getPayload()),
                    DcsMonitoringOfAuxiliaryTurbines.class, JSONReader.Feature.SupportSmartMatch);
            dcsMonitoringOfAuxiliaryTurbinesDao.save(dcsMonitoringOfAuxiliaryTurbines);
            log.info("DCS_monitoring_of_auxiliary_turbines 数据入库成功");
        }
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println(String.format("接收消息成功"));
    }
}
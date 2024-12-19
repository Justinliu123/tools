package com.example.demo.mqtt;

import com.alibaba.fastjson2.JSONObject;
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
        if(topic.equals(MqttConstant.dataTopic)) {
            ScenceDao scenceDao = SpringBeanUtil.getBean(ScenceDao.class);
            ScencePo scencePo = JSONObject.parseObject(new String(message.getPayload()), ScencePo.class);
            scenceDao.save(scencePo);
        } else if (topic.equals(MqttConstant.predictionTopic)) {
            PredictiondataDao predictiondataDao = SpringBeanUtil.getBean(PredictiondataDao.class);
            PredictiondataPo predictiondataPo = JSONObject.parseObject(new String(message.getPayload()), PredictiondataPo.class);
            predictiondataDao.save(predictiondataPo);
        } else if (topic.equals(MqttConstant.equipmentTopic)) {
            ElectricmachinedDao electricmachinedDao = SpringBeanUtil.getBean(ElectricmachinedDao.class);
            ElectricmachinedPo electricmachinedPo = JSONObject.parseObject(new String(message.getPayload()), ElectricmachinedPo.class);
            electricmachinedDao.save(electricmachinedPo);
        } else if(topic.equals(MqttConstant.equipmentStatusTopic)) {
            EquipmenttreeDao equipmenttreeDao = SpringBeanUtil.getBean(EquipmenttreeDao.class);
            EquipmenttreePo equipmenttreePo = JSONObject.parseObject(new String(message.getPayload()), EquipmenttreePo.class);
            equipmenttreeDao.save(equipmenttreePo);
        } else if(topic.equals(MqttConstant.GfDataTopic)) {
            GfScenceDao GfScenceDao = SpringBeanUtil.getBean(GfScenceDao.class);
            GfScence gfScence = JSONObject.parseObject(new String(message.getPayload()), GfScence.class);
            GfScenceDao.save(gfScence);
            log.info("GfScence数据入库成功");
        } else if(topic.equals(MqttConstant.GfPredictionTopic)) {
            GfPredictiondataDao gfPredictiondataDao = SpringBeanUtil.getBean(GfPredictiondataDao.class);
            GfPredictiondata gfPredictiondata = JSONObject.parseObject(new String(message.getPayload()), GfPredictiondata.class);
            gfPredictiondataDao.save(gfPredictiondata);
            log.info("GfPredictiondata数据入库成功");
        } else if(topic.equals(MqttConstant.GfEquipmentTopic)) {
            GfElectricmachinedDao gfElectricmachinedDao = SpringBeanUtil.getBean(GfElectricmachinedDao.class);
            GfElectricmachined gfElectricmachined = JSONObject.parseObject(new String(message.getPayload()), GfElectricmachined.class);
            gfElectricmachinedDao.save(gfElectricmachined);
            log.info("GfElectricmachined数据入库成功");
        } else if(topic.equals(MqttConstant.GfEquipmentStatusTopic)) {
            GfEquipmenttreeDao gfEquipmenttreeDao = SpringBeanUtil.getBean(GfEquipmenttreeDao.class);
            GfEquipmenttree gfEquipmenttree = JSONObject.parseObject(new String(message.getPayload()), GfEquipmenttree.class);
            gfEquipmenttreeDao.save(gfEquipmenttree);
            log.info("GfEquipmenttree数据入库成功");
        } else if(topic.equals(MqttConstant.HdDataTopic)) {
            HdScenceDao hdScenceDao = SpringBeanUtil.getBean(HdScenceDao.class);
            HdScence hdScence = JSONObject.parseObject(new String(message.getPayload()), HdScence.class);
            hdScenceDao.save(hdScence);
            log.info("HdScence数据入库成功");
        } else if(topic.equals(MqttConstant.HdEquipmentTopic)) {
            HdElectricmachinedDao hdElectricmachinedDao = SpringBeanUtil.getBean(HdElectricmachinedDao.class);
            HdElectricmachined hdElectricmachined = JSONObject.parseObject(new String(message.getPayload()), HdElectricmachined.class);
            hdElectricmachinedDao.save(hdElectricmachined);
            log.info("HdElectricmachined数据入库成功");
        } else if(topic.equals(MqttConstant.HdEquipmentStatusTopic)) {
            HdEquipmenttreeDao hdEquipmenttreeDao = SpringBeanUtil.getBean(HdEquipmenttreeDao.class);
            HdEquipmenttree hdEquipmenttree = JSONObject.parseObject(new String(message.getPayload()), HdEquipmenttree.class);
            hdEquipmenttreeDao.save(hdEquipmenttree);
            log.info("HdEquipmenttree数据入库成功");
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
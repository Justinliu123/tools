package com.example.demo.websocket.encoder;

import com.example.demo.dto.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

/**
 * 服务器编码器
 *
 * @author 33099
 * @date 2023/06/09
 */
public class ServerEncoder implements Encoder.Text<ResponseMessage<Object>> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public String encode(ResponseMessage responseMessage) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(responseMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
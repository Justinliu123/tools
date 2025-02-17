package com.example.demo.config.Json2webSeralizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleToStringSerializer extends JsonSerializer<Double> {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(DECIMAL_FORMAT.format(value));
        } else {
            gen.writeNull();
        }
    }
}
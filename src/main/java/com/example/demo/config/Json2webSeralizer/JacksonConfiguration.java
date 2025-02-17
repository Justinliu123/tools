package com.example.demo.config.Json2webSeralizer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			// 设置日期格式
			builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
			// 解决long类型损失精度
			builder.serializerByType(Long.class, ToStringSerializer.instance);
			// 注册自定义的Double序列化器 日期格式自定义类
			SimpleModule module = new SimpleModule();
			module.addSerializer(Double.class, new DoubleToStringSerializer());
			builder.modules(module, new JavaTimeModule());
		};
	}

}

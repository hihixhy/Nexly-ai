package com.nexlyai.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// 直接在注解里排除数据源自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.nexlyai.backend.mapper")
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}

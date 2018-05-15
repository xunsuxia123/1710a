package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Scan
 */

@SpringBootApplication
@MapperScan("com.jk.dao")
public class Demo123Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo123Application.class, args);
	}

	@Bean
	public Queue getQueue(){
		return new Queue("1369");
	}

}

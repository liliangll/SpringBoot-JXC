package com.bdqn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	/**
     *新增此方法
     */
    /* protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       // 注意这里要指向原先用main方法执行的Application启动类
       return builder.sources(Application.class);
   }*/
}

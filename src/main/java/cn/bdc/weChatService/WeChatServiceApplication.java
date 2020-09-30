package cn.bdc.weChatService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class WeChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeChatServiceApplication.class, args);
	}
}

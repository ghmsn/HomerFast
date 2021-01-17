package cn.homeron.homerfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableRedisHttpSession
@EnableOpenApi
@SpringBootApplication
public class HomerFastApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomerFastApplication.class, args);
	}
}

package cn.homeron.homerfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class HomerFastApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomerFastApplication.class, args);
	}
}

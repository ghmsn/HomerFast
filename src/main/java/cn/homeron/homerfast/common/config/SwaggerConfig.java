package cn.homeron.homerfast.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class SwaggerConfig {

//    @Bean
//    public Docket createRestApi(){
//		return new Docket(DocumentationType.SWAGGER_2).
//				useDefaultResponseMessages(false)
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build()
////				.globalOperationParameters(parameters)
//				.securitySchemes(securitySchemes())
//				.securityContexts(securityContexts());
//	}
//
//    private ApiInfo apiInfo(){
//    	return new ApiInfoBuilder()
//    			.title("Wechat接口文档")
//    			.description("This is a restful api document of Wechat.")
//    			.version("1.0")
//    			.build();
//    }

//	private List<ApiKey> securitySchemes() {
//		return newArrayList(new ApiKey("Authorization", "Authorization", "header"));
//	}
//	private List<SecurityContext> securityContexts() {
//		return newArrayList(SecurityContext.builder()
//						.securityReferences(defaultAuth())
//						.forPaths(PathSelectors.any())
//						.build()
//		);
//	}
//	List<SecurityReference> defaultAuth() {
//		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//		authorizationScopes[0] = authorizationScope;
//		return newArrayList(new SecurityReference("Authorization", authorizationScopes));
//	}

}